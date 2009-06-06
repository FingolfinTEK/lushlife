package lushfile.plugins;

import lushfile.plugins.context.ContextModule;
import lushfile.plugins.controller.ControllerModule;
import lushfile.plugins.resources.ResourceModule;
import lushfile.plugins.view.ViewModule;

import com.google.inject.Binder;
import com.google.inject.Module;

public class PluginModules implements Module {

	@Override
	public void configure(Binder binder) {
		binder.install(new ControllerModule());
		binder.install(new ViewModule());
		binder.install(new ContextModule());
		binder.install(new ResourceModule());
	}

}
