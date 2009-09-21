package org.lushlife.kamikaze;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContext;

import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class Kamikaze {

	static Log log = Logging.getLog(Kamikaze.class);

	public static boolean isHotdeployMode() {
		ServletContext context = org.lushlife.kamikaze.KamikazeContext
				.getServletContext();
		String str = context.getServerInfo();
		if (!str.contains("Development")) {
			return false;
		}
		return true;
	}

	static public <T> T $(Class<T> type, Annotation... bindings) {
		return getInjector().getInstance(type, bindings);
	}

	static public Injector getInjector() {
		BeanManager beanManager = KamikazeContext.get(BeanManager.class);
		log.log(LogMsgCore.KMK00004, beanManager.hashCode(), beanManager);
		Bean<?> injectorBean = beanManager.getBeans(Injector.class).iterator()
				.next();
		return (Injector) beanManager.getReference(injectorBean,
				Injector.class, beanManager
						.createCreationalContext(injectorBean));
	}

}
