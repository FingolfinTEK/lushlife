package lushfile.plugins.resources;

import lushfile.core.controller.LushControllerMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class ResourceConfigurator {

	@Inject
	public void init(LushControllerMetadata metadata,
			Injector injector) {

		metadata.getPackageMapping().put(
				"Resources",
				injector.getInstance(ResourceController.class).init(
						this.getClass().getPackage()));

	}

}
