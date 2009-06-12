package lush.plugins.yahoo;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class YahooConfigurator {

	@Inject
	public void init(LushControllerMetadata meta, Injector injector) {
		meta.getPackageMapping().put("Yahoo",
				injector.getInstance(LushController.class)//
						.init(this.getClass().getPackage()));
	}
}
