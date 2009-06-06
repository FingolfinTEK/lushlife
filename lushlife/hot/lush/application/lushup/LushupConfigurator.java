package lush.application.lushup;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;
import lushfile.plugins.resources.LoadScriptMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class LushupConfigurator {

	@Inject
	public void init(LushControllerMetadata metadata, Injector injector) {
		metadata.getPackageMapping().put(
				"Lushup",
				injector.getInstance(LushController.class).init(
						LushupConfigurator.class.getPackage().getName()));
	}

	@Inject
	public void init(LoadScriptMetadata metadata) {
		metadata.getResources().put("jquery",
				"http://ajax.googleapis.com/ajax/libs/jquery/1.3.1/jquery.js");
	}

}
