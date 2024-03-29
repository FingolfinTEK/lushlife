package ex.objectify.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

public class ObjectifyManager implements InitializingBean {
	static Logger logger = LoggerFactory.getLogger(ObjectifyManager.class);

	ObjectifyFactory objectifyFactory;

	ThreadLocal<Objectify> transactionalObjectify = new ThreadLocal<Objectify>();

	private String basePackage;

	private List<Class<?>> classes;

	public Objectify getProxy() {
		return (Objectify) Proxy.newProxyInstance(
				Objectify.class.getClassLoader(),
				new Class[] { Objectify.class }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						try {
							return method.invoke(get(), args);
						} catch (InvocationTargetException ex) {
							throw ex.getCause();
						}
					}
				});
	}

	public Objectify get() {
		Objectify objectify = transactionalObjectify.get();
		if (objectify != null) {
			logger.debug("get objectify with transaction");
			return objectify;
		}
		logger.debug("get objectify with no transaction");
		return objectifyFactory.begin();
	}

	public Objectify getOrCreateObjectifyWithTransaction() {
		Objectify objectify = transactionalObjectify.get();
		if (objectify == null) {
			objectify = objectifyFactory.beginTransaction();
			transactionalObjectify.set(objectify);
		}
		return objectify;
	}

	public void afterPropertiesSet() throws Exception {
		if (classes == null) {
			classes = new ArrayList<Class<?>>();
		}

		if (basePackage != null && basePackage.length() > 0) {
			classes.addAll(doScan());
		}

		this.objectifyFactory = new ObjectifyFactory();
		for (Class<?> clazz : classes) {
			logger.info("Objectify Entity {}", clazz);
			this.objectifyFactory.register(clazz);
		}

	}

	protected List<Class<?>> doScan() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String[] basePackages = StringUtils.tokenizeToStringArray(
				this.basePackage,
				ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		for (String basePackage : basePackages) {
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
					false);
			scanner.addIncludeFilter(new AnnotationTypeFilter(
					com.googlecode.objectify.annotation.Entity.class));
			scanner.addIncludeFilter(new AnnotationTypeFilter(
					javax.persistence.Entity.class));
			Set<BeanDefinition> candidates = scanner
					.findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				Class<?> clazz = ClassUtils.resolveClassName(
						candidate.getBeanClassName(),
						ClassUtils.getDefaultClassLoader());
				classes.add(clazz);
			}
		}
		return classes;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setClasses(List<Class<?>> classes) {
		this.classes = classes;
	}

	public void clear() {
		transactionalObjectify.remove();
	}

}
