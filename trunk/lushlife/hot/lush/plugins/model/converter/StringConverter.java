package lush.plugins.model.converter;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import lush.plugins.model.PropertyConverter;

public class StringConverter implements PropertyConverter<String> {

	@Override
	public String convert(HttpServletRequest request, Type type,
			String propertyName) {
		return request.getParameter(propertyName);
	}

}
