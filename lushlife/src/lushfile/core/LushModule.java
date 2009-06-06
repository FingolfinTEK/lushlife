package lushfile.core;

import lushfile.core.context.ClassLoaderManager;
import lushfile.core.context.LushContext;
import lushfile.core.guice.ServletScope;
import lushfile.core.guice.ServletScoped;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class LushModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bindScope(ServletScoped.class, new ServletScope());
		binder.bind(String.class).annotatedWith(Names.named("encoding"))
				.toInstance("UTF-8");
		binder.bind(Long.class).annotatedWith(Names.named("startupTime"))
				.toInstance(System.currentTimeMillis());

	}

	@Provides
	public ClassLoader getClassLoader() {
		return ClassLoaderManager.getClassLoader(LushContext
				.getServletContext());
	}
}
