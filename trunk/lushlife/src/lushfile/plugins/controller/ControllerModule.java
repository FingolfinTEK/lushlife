package lushfile.plugins.controller;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushEventManager;
import lushfile.core.controller.RequestAnalyzer;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class ControllerModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(RequestAnalyzer.class).to(DefaultRequestAnalyzer.class);
		binder.bind(LushEventManager.class).to(DefaultLushEventManager.class);
		binder.bind(LushController.class).to(PackageLushController.class);

		binder.bind(String.class).annotatedWith(Names.named("commandKey"))
				.toInstance("_lush_command_");
	}

}
