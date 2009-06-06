package lushfile.plugins.resources;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ResourceModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(ResourceConfigurator.class).asEagerSingleton();

	}

}
