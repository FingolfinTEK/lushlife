package lush.plugins.maven;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class MavenConfigurator {

	@Inject
	public void init(LushControllerMetadata meta, Injector injector) {
		meta.getPackageMapping().put("Maven",
				injector.getInstance(LushController.class)//
						.init(this.getClass().getPackage()));
	}
}
