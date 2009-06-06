package lushfile.core.controller;

public class ReflectionErrorParameter {
	private String packageName;
	private String className;
	private String methodName;
	private Exception exception;

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public Exception getException() {
		return exception;
	}

	public ReflectionErrorParameter init(String packageName, String className,
			String methodName, Exception e) {
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.exception = e;
		return this;
	}

	public String getPackageName() {
		return packageName;
	}

}
