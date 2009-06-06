package lushfile.plugins.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;

import lushfile.core.context.LushNamingResolver;
import lushfile.core.util.BeansUtil;

@RequestScoped
public class DefaultNamingResolver implements LushNamingResolver {

	Logger logger = LoggerFactory.getLogger(DefaultNamingResolver.class);

	@Inject
	private Injector injector;

	@Inject
	private ClassLoader loader;

	private String basePackage;

	@Override
	public Object getInstneceByName(String name) {
		String className = basePackage + ".model."
				+ BeansUtil.toClassName(name);
		try {
			Class<?> clazz = loader.loadClass(className);
			return injector.getInstance(clazz);
		} catch (ClassNotFoundException e) {
//			if (logger.isDebugEnabled()) {
//				logger.debug("not found " + className, e);
//			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public DefaultNamingResolver init(String basePackage) {
		this.basePackage = basePackage;
		return this;
	}

}
