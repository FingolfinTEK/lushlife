package lushfile.plugins.context;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class RequestContext {

	private String contextName;

	private String packageName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getContextName() {
		return contextName;
	}

	public String[] getParameter() {
		return parameter;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	String baseName;

	public String getBaseName() {
		return baseName;
	}

	@Inject
	public RequestContext(HttpServletRequest request) {
		String str = request.getPathInfo().trim();
		String requestUri = request.getRequestURI();
		this.baseName = requestUri.substring(1, requestUri.length()
				- str.length());
		init(str);
	}

	public RequestContext(String str) {
		init(str);
	}

	protected void init(String str) {

		if (str.length() == 0) {
			setContextName(defaultContextName());
			setControllerName(defaultController());
			setMethodName(defaultMethod());
			setParameter(defaultParameter());
			return;
		}

		String[] tmps = str.substring(1).split("/");
		int point = 0;

		// context
		if (point < tmps.length) {
			if (tmps[point].isEmpty()) {
				setContextName(defaultContextName());
				point++;
			} else if (Character.isJavaIdentifierStart(tmps[point].charAt(0))) {
				setContextName(tmps[point]);
				point++;
			} else {
				setContextName(defaultContextName());
			}
		} else {
			setContextName(defaultContextName());
		}

		// class
		if (point < tmps.length) {
			if (tmps[point].isEmpty()) {
				setControllerName(defaultController());
				point++;
			} else if (Character.isUpperCase(tmps[point].charAt(0))) {
				setControllerName(tmps[point]);
				point++;
			} else {
				setControllerName(defaultController());
			}
		} else {
			setControllerName(defaultController());
		}

		// method
		if (point < tmps.length) {
			if (tmps[point].isEmpty()) {
				setMethodName(defaultMethod());
				point++;
			} else if (Character.isJavaIdentifierStart(tmps[point].charAt(0))) {
				setMethodName(tmps[point]);
				point++;
			} else {
				setMethodName(defaultMethod());
			}
		} else {
			setMethodName(defaultMethod());
		}

		// parameter
		if (point < tmps.length) {
			String[] params = new String[tmps.length - point];
			System.arraycopy(tmps, point, params, 0, params.length);
			setParameter(params);
		} else {
			setParameter(defaultParameter());
		}

	}

	protected String defaultContextName() {
		return "";
	}

	String[] parameter;

	public void setParameter(String[] parameter) {
		this.parameter = parameter;
	}

	private String method;

	public String getMethodName() {
		return method;
	}

	public void setMethodName(String method) {
		this.method = method;

	}

	private String controllerName;

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getControllerName() {
		return controllerName;
	}

	protected String[] defaultParameter() {
		return new String[0];
	}

	protected String defaultMethod() {
		return "invoke";
	}

	protected String defaultController() {
		return getContextName();
	}
}