package org.lushlife.negroni.conversions;

public interface Converter<T> {

	public boolean isAssignableTo(Class<?> from);

	public T convert(Object obj);

}
