package lushfile.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletContext;

import lushfile.core.context.ClassLoaderManager;
import lushfile.core.context.LushContext;
import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;
import lushfile.core.guice.InjectorFactory;
import lushfile.core.guice.ModuleLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class LushLife {

	static Logger log = LoggerFactory.getLogger(LushLife.class);
	static private InjectorFactory factory = new InjectorFactory() {
		@Override
		public Injector createInjector() {
			ServletContext context = LushContext.getServletContext();
			ClassLoader loader = ClassLoaderManager.getClassLoader(context);
			List<Module> modules = ModuleLoader.loadModules(loader);
			return Guice.createInjector(modules);
		}
	};

	public static boolean isHotdeployMode() {
		ServletContext context = LushContext.getServletContext();
		URL url;
		try {
			String str = context.getServerInfo();
			if (!str.contains("Development")) {
				return false;
			}
			url = context.getResource("/WEB-INF/hot.properties");
			return url != null;
		} catch (MalformedURLException e) {
			log.warn("load resource failed", e);
		}
		return false;

	}

	public static void resetInjector() {
		ServletContext context = LushContext.getServletContext();
		context.removeAttribute(Injector.class.getName());
	}

	public static void setInjectorFactory(InjectorFactory factory) {
		LushLife.factory = factory;
	}

	public static Injector getInjector() {
		ServletContext context = LushContext.getServletContext();
		Injector injector = (Injector) context.getAttribute(Injector.class
				.getName());
		if (injector == null) {
			synchronized (LushLife.class) {
				injector = (Injector) context.getAttribute(Injector.class
						.getName());
				if (injector == null) {
					injector = factory.createInjector();
					context.setAttribute(Injector.class.getName(), injector);
				}
			}
		}
		return injector;
	}

	static public String resolvePackage(String contextName) {
		LushController controller = getInjector().getInstance(
				LushControllerMetadata.class).getPackageMapping().get(
				contextName);
		if (controller != null) {
			return controller.getPackageName();
		}
		return contextName.toLowerCase();
	}
}
