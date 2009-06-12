package lush.plugins.model.converter;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import lush.plugins.model.PropertyConverter;

public class LongConverter implements PropertyConverter<Long> {

	@Override
	public Long convert(HttpServletRequest request, Type type,
			String propertyName) {
		String paramter = request.getParameter(propertyName);
		if (paramter == null) {
			return null;
		}
		return Long.parseLong(paramter);
	}

}
