package negroni.core.impl.delegate;

import java.lang.reflect.Method;

public abstract class AbstractVarArgsDelegatemMethod extends
		AbstractDelegateMethod {

	private int varArgsPosition;
	private Class<?> varArgsType;

	public AbstractVarArgsDelegatemMethod(Method method) {
		super(method);
		Class<?>[] typs = method.getParameterTypes();
		this.varArgsPosition = typs.length - 1;
		this.varArgsType = typs[typs.length - 1].getComponentType();
	}

	public int getVarArgsPosition() {
		return varArgsPosition;
	}

	public Class<?> getVarArgsType() {
		return varArgsType;
	}
}
