package lush.plugins.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import lushfile.core.util.BeansUtil;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class DefaultModelUpdate implements ModelUpdater {

	@Inject
	private Injector injector;

	@Inject
	private ConverterMetadata metadata;

	@Inject
	HttpServletRequest request;

	@Override
	public <T> T update(Class<T> clazz) {
		T item = injector.getInstance(clazz);
		String clazzName = BeansUtil.toPropertyName(clazz.getSimpleName());
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			for (PropertyDescriptor pd : properties) {
				Method method = pd.getWriteMethod();
				if (method == null) {
					continue;
				}
				String propertyName = clazzName + "." + pd.getName();
				Class<?> type = method.getParameterTypes()[0];
				PropertyConverter<?> converter = metadata.getConverter(type);
				Object value = converter.convert(request, method
						.getGenericParameterTypes()[0], propertyName);
				if (value != null) {
					method.invoke(item, value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return item;
	}

}
