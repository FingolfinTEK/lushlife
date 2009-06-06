package lushfile.plugins.controller;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;
import lushfile.core.controller.RequestAnalyzer;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class DefaultRequestAnalyzer implements RequestAnalyzer {

	@Inject
	private LushControllerMetadata metadata;

	@Inject
	private Injector injector;

	@Inject
	private RequestContext parameter;

	@Override
	public void analyze() {
		resolveController().controll();
	}

	private LushController resolveController() {
		LushController controller = metadata.getPackageMapping().get(
				parameter.getContextName());
		if (controller == null) {
			return defaultController(parameter.getContextName());
		}
		return controller;
	}

	protected LushController defaultController(String packageName) {
		return injector.getInstance(LushController.class).init(packageName);
	}

}
