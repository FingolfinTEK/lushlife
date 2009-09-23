package negroni;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import negroni.core.exceptions.NegroniException;


/**
 * @author k2.junior.s@gmail.com
 */
public abstract class Mix<T> {
	final Object obj;
	private Object[] args = new Object[0];

	public Mix(Object obj, Object... args) {
		this.obj = obj;
		this.args = args;
	}

	public Class<T> getActualType() {
		if (!(getClass().getGenericSuperclass() instanceof ParameterizedType)) {
			return null;
		}
		ParameterizedType ty = (ParameterizedType) getClass()
				.getGenericSuperclass();
		Type actualType = ty.getActualTypeArguments()[0];
		if (actualType instanceof Class) {
			return (Class<T>) actualType;
		}
		throw new NegroniException("no parameterized type");
	}

	public Type getType() {
		if (!(getClass().getGenericSuperclass() instanceof ParameterizedType)) {
			return null;
		}
		ParameterizedType ty = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return ty.getActualTypeArguments()[0];
	}

	public Object getObj() {
		return obj;
	}

	public Object[] getArgs() {
		return args;
	}

}
