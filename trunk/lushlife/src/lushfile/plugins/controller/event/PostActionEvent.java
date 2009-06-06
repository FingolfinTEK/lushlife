package lushfile.plugins.controller.event;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import lushfile.core.controller.LushEvent;
import lushfile.core.util.BeansUtil;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class PostActionEvent extends ActionEvent implements LushEvent {

	private String className;
	private String methodName;

	@Inject
	public PostActionEvent(HttpServletRequest request,
			@Named("commandKey") String commandKey) {
		// String command = request.getParameter(commandKey);
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String param = paramNames.nextElement();
			if (param.startsWith(commandKey + "##")) {
				String command = param.substring((commandKey + "##").length());
				if (command != null) {
					String[] str = command.split("\\.");
					if (str.length >= 1) {
						className = BeansUtil.toClassName(str[0]);
					}
					if (str.length >= 2) {
						methodName = str[1];
					}
				}
				return;
			}
		}
	}

	protected String resolveActionMethod() {
		if (this.methodName != null) {
			return this.methodName;
		}
		return parameter.getMethodName();
	}

	protected String resolveActionClass() {
		if (className != null) {
			return packageName + ".action." + className;
		}
		return packageName + ".action." + parameter.getControllerName();
	}

}
