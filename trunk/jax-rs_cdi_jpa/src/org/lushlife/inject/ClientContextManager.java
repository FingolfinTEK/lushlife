package org.lushlife.inject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.context.beanstore.HashMapBeanStore;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;

@RequestScoped
public class ClientContextManager {

	private String key = "__lush_hidden__";

	private Map<String, ContextualInstance<? extends Object>> store;
	private HashMapBeanStore currentBeanStore;

	@Inject
	private ClientContext hiddenContext;

	@Inject
	BeanManager manager;

	public <T> void begin(HttpServletRequest request) {
		String hidden = request.getParameter(key);
		this.currentBeanStore = new HashMapBeanStore();
		store = currentBeanStore.delegate();

		hiddenContext.setActive(true);
		hiddenContext.setBeanStore(currentBeanStore);
		if (hidden != null) {
			Map<Contextual<?>, Object> target = decode(hidden);
			for (Entry<Contextual<?>, Object> entry : target.entrySet()) {
				Contextual<T> contextual = (Contextual<T>) entry.getKey();

				Bean<?> bean = toBean(contextual);

				Object object = manager.getReference(bean, entry.getValue()
						.getClass(), manager.createCreationalContext(bean));
				try {
					BeanUtils.copyProperties(object, entry.getValue());
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				} catch (InvocationTargetException e) {
					throw new IllegalStateException(e);
				}
			}
		}

		request.setAttribute("hidden", this);
	}

	private Bean<?> toBean(Contextual<?> contextual) {
		if (contextual instanceof SerializableContextual) {
			return toBean(((SerializableContextual) contextual).get());
		}
		if (contextual instanceof Bean<?>) {
			return (Bean<?>) contextual;
		}
		throw new UnsupportedOperationException("unsuported Contextual "
				+ contextual);

	}

	private String encode(Map<String, ContextualInstance<? extends Object>> map) {
		try {
			Map<Contextual<?>, Object> target = new HashMap<Contextual<?>, Object>();
			for (Entry<String, ContextualInstance<? extends Object>> entry : map
					.entrySet()) {
				ContextualInstance<? extends Object> value = entry.getValue();
				target.put(value.getContextual(), value.getInstance());
			}

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bao);
			oos.writeObject(target);
			oos.close();
			bao.close();
			byte[] array = bao.toByteArray();
			byte[] encode = Base64.encodeBase64(array);
			return new String(encode);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private Map<Contextual<?>, Object> decode(String hidden) {
		try {
			byte[] decode = Base64.decodeBase64(hidden.getBytes());
			ByteArrayInputStream stream = new ByteArrayInputStream(decode);
			ObjectInputStream ois = new ObjectInputStream(stream);
			try {
				return (Map<Contextual<?>, Object>) ois.readObject();
			} finally {
				ois.close();
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	public void end() {
		hiddenContext.setActive(false);
		hiddenContext.setBeanStore(null);
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return encode(store);
	}

	public String getParameter() {
		return "<input type=\"hidden\" name=\"" + key + "\" value=\""
				+ encode(store) + "\">";
	}

	public void destroy() {
		ClientContext mock = new ClientContext();
		mock.setActive(true);
		mock.setBeanStore(this.currentBeanStore);
		mock.destroy();
		mock.setActive(false);
		mock.setBeanStore(null);

		this.currentBeanStore = new HashMapBeanStore();
		this.store = currentBeanStore.delegate();
		hiddenContext.setBeanStore(currentBeanStore);
	}

}
