package lushfile.plugins.view;

import lushfile.core.controller.ReflectionErrorParameter;
import lushfile.core.view.ErrorViewHandler;

public class DefaultErrorViewHandler implements ErrorViewHandler {

	@Override
	public void handle(ReflectionErrorParameter param) {
		// TODO Error Handling
		throw new RuntimeException(param.getClassName() + "#"
				+ param.getMethodName(), param.getException());
	}
}
