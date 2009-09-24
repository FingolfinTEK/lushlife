package org.lushlife.kamikaze.bootstrap;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.lushlife.kamikaze.LogMsgKMKZC;
import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.util.loader.ServiceLoader;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class BeanBinderImpl implements WebBeansBinder {
	static public Log log = Logging.getLog(BeanBinderImpl.class);

	private Set<Class<?>> classes = new HashSet<Class<?>>();
	private Set<URL> xmls = new HashSet<URL>();

	public Set<Class<?>> getClasses() {
		return classes;
	}

	public Set<URL> getXmls() {
		return xmls;
	}

	public void model(Class<?> clazz) {
		log.log(LogMsgKMKZC.KMKZC0008, clazz);
		classes.add(clazz);
	}

	public void install(WebBeansModule module) {
		module.configure(this);
	}

	public void beansXml(URL url) {
		xmls.add(url);
	}

	public void installService(Class<? extends WebBeansModule> clazz) {
		for (WebBeansModule module : ServiceLoader.load(clazz)) {
			install(module);
		}
	}
}