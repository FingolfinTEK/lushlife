package negroni.core.impl.inject;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import negroni.Configurator;
import negroni.Identifier;
import negroni.annotation.Config;
import negroni.annotation.literal.NamedAnnotationLiteral;
import negroni.core.impl.reflection.Reflections;


public class Injections {

	static public List<InjectionUnit<?>> toInjectionUnits(Class<?> type,
			Enum<?> e, Configurator initializer) {
		List<InjectionUnit<?>> list = new ArrayList<InjectionUnit<?>>();
		for (Field f : Reflections.getEnumFields(e)) {
			f.setAccessible(true);
			InjectionUnit<?> unit = toInjectionUnit(type, e, f, initializer);
			if (unit != null) {
				list.add(unit);
			}
		}
		return list;
	}

	static public <T, K> InjectionUnit<K> toInjectionUnit(Class<T> type,
			Enum<?> e, Field f, Configurator initializer) {
		Object value = Reflections.get(e, f);
		String name = f.getName();
		InjectedValue injectedValue = null;
		if (value instanceof Enum
				&& Reflections.isEnumAnnotationPresent((Enum) value,
						Config.class)) {
			Config binding = Reflections.getEnumAnnotation((Enum) value,
					Config.class);
			Identifier<?> id = Injections.toId(initializer, (Enum) value,
					binding);
			injectedValue = new ReferencedInjectedValue(id);
		} else {
			injectedValue = new ConstInjectedValue(value);
		}

		Injection<?> injection = null;
		for (PropertyDescriptor pd : Reflections.getBeanInfo(type)
				.getPropertyDescriptors()) {
			if (pd.getName().equals(name) && pd.getWriteMethod() != null) {
				injection = new MethodInjection(name, pd.getWriteMethod());
				break;
			}
		}
		if (injection == null) {
			Field field = Reflections.getField(type, name);
			// Emmaがprivate Fieldを追加するため、
			// Final Fieldは変更できないように変更
			if ((field.getModifiers() & Modifier.FINAL) != 0) {
				return null;
			}
			injection = new FiledInjection(field);
		}
		return new InjectionUnit(injection, injectedValue);

	}

	public static Identifier<?> toId(Configurator configurator, Enum<?> e,
			Config binding) {
		if (binding.named()) {
			return configurator.toId(binding.type(),
					new NamedAnnotationLiteral(e.name()));

//			return configurator.toId(binding.type(),
//					new NamedImplPublic(e.name()));
		} else {
			return configurator.toId(binding.type(), null);
		}
	}
}
