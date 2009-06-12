package lushfile.plugins.view;

import lushfile.core.controller.ReflectionErrorParameter;
import lushfile.core.view.ErrorViewHandler;

public class DefaultErrorViewHandler implements ErrorViewHandler {

	@Override
	public void handle(ReflectionErrorParameter param) {
		throw new RuntimeException("#" + param.getMethod(), param
				.getException());
	}
}
