package lush.plugins.jquery;

import com.google.inject.Inject;
import com.google.inject.Injector;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;
import lushfile.plugins.view.ViewUtilMetadata;
import lushfile.plugins.view.ViewUtilVinding;

public class JQueryConfiguration {

	@Inject
	public void init(LushControllerMetadata meta, Injector injector) {
		meta.getPackageMapping().put("JQuery",
				injector.getInstance(LushController.class)//
						.init(this.getClass().getPackage()));
	}

}
