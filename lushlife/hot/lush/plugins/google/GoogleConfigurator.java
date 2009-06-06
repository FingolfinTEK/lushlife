package lush.plugins.google;

import com.google.inject.Inject;
import com.google.inject.Injector;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;
import lushfile.plugins.view.ViewUtilMetadata;
import lushfile.plugins.view.ViewUtilVinding;

public class GoogleConfigurator {

	@Inject
	public void init(LushControllerMetadata meta, Injector injector) {
		meta.getPackageMapping().put("Google",
				injector.getInstance(LushController.class)//
						.init(this.getClass().getPackage()));
	}

	@Inject
	public void init(ViewUtilMetadata meta) {
		meta.getBindingItems().put("g", GFunction.class);
	}

}
