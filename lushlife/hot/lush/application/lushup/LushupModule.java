package lush.application.lushup;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import lush.plugins.google.GoogleModule;
import lush.plugins.jquery.JQueryModule;
import lush.plugins.lfunction.LFunctionModule;
import lush.plugins.model.ModelModule;
import lush.plugins.yahoo.YahooModule;
import lushfile.core.LushLife;
import lushfile.core.guice.ServletScoped;
import lushfile.plugins.LushCorePluginModules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

public class LushupModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.install(new LushCorePluginModules());
		binder.install(new ModelModule());

		binder.install(new GoogleModule());
		binder.install(new YahooModule());
		binder.install(new JQueryModule());

		binder.install(new LFunctionModule());
		binder.bind(LushupConfigurator.class).asEagerSingleton();
	}

	@Provides
	@ServletScoped
	public PersistenceManagerFactory createFactor() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional");
	}

	@Provides
	@RequestScoped
	public PersistenceManager createManagerFactory() {
		return LushLife.getInjector().getInstance(
				PersistenceManagerFactory.class).getPersistenceManager();
	}
}
