package lushfile.plugins.controller.event;

import lushfile.core.controller.LushEvent;

public class GetActionEvent extends ActionEvent implements LushEvent {

	protected String resolveActionMethod() {
		String methodName = parameter.getMethodName();
		return methodName;
	}

	protected String resolveActionClass() {
		String className = packageName + ".action."
				+ parameter.getControllerName();
		return className;
	}

}
