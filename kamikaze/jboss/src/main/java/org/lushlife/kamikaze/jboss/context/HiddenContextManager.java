package org.lushlife.kamikaze.jboss.context;
//package org.lushlife.scarecrow.jboss.context;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.ObjectStreamClass;
//import java.util.Map;
//
//import javax.enterprise.context.spi.Contextual;
//import javax.inject.Inject;
//import javax.servlet.ServletRequest;
//
//import org.jboss.webbeans.context.api.ContexutalInstance;
//import org.lushlife.glassbottle2.GlassBottleContext;
//import org.lushlife.glassbottle2.context.binding.HiddenKey;
//
//public class HiddenContextManager {
//
//	public String getHiddenKey() {
//		return hiddenKey;
//	}
//
//	@HiddenKey
//	private String hiddenKey;
//
//	public String toHidden() {
//		Map<Contextual<? extends Object>, Object> hidden = GlassBottleContext
//				.getHiddenScope();
//		if (hidden.size() == 0) {
//			return "";
//		}
//		try {
//			ByteArrayOutputStream bao = new ByteArrayOutputStream();
//			ObjectOutputStream oos = new ObjectOutputStream(bao);
//			oos.writeObject(hidden);
//			oos.flush();
//			byte[] array = bao.toByteArray();
//			byte[] base64 = Base64.encodeBase64(array);
//			oos.close();
//			return new String(base64);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} finally {
//		}
//	}
//
//	@Inject
//	ClassLoader loader;
//
//	public void restore(ServletRequest req) {
//		String value = req.getParameter(hiddenKey);
//		if (value != null) {
//			if (value.length() == 0) {
//				return;
//			}
//			byte[] base64 = value.getBytes();
//			byte[] object = Base64.decodeBase64(base64);
//			try {
//				ByteArrayInputStream input = new ByteArrayInputStream(object);
//				ObjectInputStream ois = new ObjectInputStream(input) {
//					@Override
//					protected Class<?> resolveClass(ObjectStreamClass desc)
//							throws IOException, ClassNotFoundException {
//						String className = desc.getName();
//						return loader.loadClass(className);
//					}
//				};
//				Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>> map = (Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>>) ois
//						.readObject();
//				ois.close();
//				GlassBottleContext.getHiddenScope().putAll(map);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			} catch (ClassNotFoundException e) {
//				throw new RuntimeException(e);
//			}
//		}
//	}
// }
