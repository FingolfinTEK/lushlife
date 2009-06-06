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

	@Inject
	PageNavigatorMetadata metadata;

	@Override
	public void handle(ActionParameter fromAction) {
		String viewId = resolveViewId(fromAction);

		// String classPath = defaultPackage(fromAction) +viewId;
		try {
			injector.getInstance(LushNamingResolver.class).init(
					fromAction.getPackageName());
			injector.getInstance(Renderer.class).init(viewId).render();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String defaultPackage(ActionParameter fromAction) {
		return fromAction.getPackageName().replace(".", "/") + "/view";
	}

	protected String resolveViewId(ActionParameter fromAction) {
		Object obj = fromAction.getFromAction();
		if (obj instanceof String) {
			String str = (String) obj;
			if (str.startsWith("/")) {
				return defaultPackage(fromAction) + str;
			}
		}

		PageNavigator pageNavigator = metadata.getNavigators().get(
				fromAction.getPackageName());
		if (pageNavigator != null) {
			String page = pageNavigator.navigate(fromAction);
			if (page != null) {
				if (page.startsWith("/")) {
					return defaultPackage(fromAction) + page;
				}
				return page;
			}
		}
		return defaultPackage(fromAction) + "/index.gsp";
	}
}
