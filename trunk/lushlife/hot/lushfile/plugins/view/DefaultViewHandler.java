package lushfile.plugins.view;

import java.io.IOException;

import lushfile.core.context.LushNamingResolver;
import lushfile.core.controller.ActionParameter;
import lushfile.core.view.Renderer;
import lushfile.core.view.ViewHandler;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class DefaultViewHandler implements ViewHandler {

	@Inject
	Injector injector;

	@Override
	public void handle(ActionParameter fromAction) {
		String viewId = resolveViewId(fromAction);

		String classPath = fromAction.getPackageName().replace(".", "/")
				+ "/view" + viewId;
		try {
			injector.getInstance(LushNamingResolver.class).init(
					fromAction.getPackageName());
			injector.getInstance(Renderer.class).init(classPath).render();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private String resolveViewId(ActionParameter fromAction) {
		return "/index.gsp";
	}

}
