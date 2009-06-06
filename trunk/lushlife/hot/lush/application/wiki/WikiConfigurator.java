package lush.application.wiki;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;
import lushfile.plugins.resources.LoadScriptMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class WikiConfigurator {

	@Inject
	public void init(LushControllerMetadata metadata, Injector injector) {
		metadata.getPackageMapping().put(
				"Wiki",
				injector.getInstance(LushController.class).init(
						WikiConfigurator.class.getPackage().getName()));
	}

	@Inject
	public void init(LoadScriptMetadata metadata) {
		metadata.getResources().put("jquery",
				"http://ajax.googleapis.com/ajax/libs/jquery/1.3.1/jquery.js");
	}

}
