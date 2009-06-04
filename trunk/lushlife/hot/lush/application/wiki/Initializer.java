package lush.application.wiki;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class Initializer {

	@Inject
	public Initializer(LushControllerMetadata metadata, Injector injector) {
		metadata.getPackageMapping().put(
				"Wiki",
				injector.getInstance(LushController.class).init(
						Initializer.class.getPackage().getName()));
	}

}
