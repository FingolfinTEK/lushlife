package lush.application.lushup;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import lush.plugins.google.GoogleModule;
import lush.plugins.lfunction.LFunctionModule;
import lushfile.plugins.LushCorePluginModules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

public class LushupModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.install(new LushCorePluginModules());
		binder.install(new GoogleModule());
		binder.install(new LFunctionModule());

		binder.bind(LushupConfigurator.class).asEagerSingleton();
	}

	PersistenceManagerFactory factory = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	@Provides
	@RequestScoped
	public PersistenceManager createManagerFactory() {
		return factory.getPersistenceManager();
	}
}
