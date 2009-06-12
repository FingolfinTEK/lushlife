package lush.plugins.model;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

public interface PropertyConverter<T> {

	T convert(HttpServletRequest request, Type type, String propertyName);

}
