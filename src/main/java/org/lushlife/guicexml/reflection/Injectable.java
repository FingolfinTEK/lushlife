package org.lushlife.guicexml.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public interface Injectable {
	static public class FieldInject implements Injectable {
		private Field field;

		public FieldInject(Field field) {
			field.setAccessible(true);
			this.field = field;
		}

		@Override
		public Type getType() {
			return field.getGenericType();
		}

		@Override
		public void setValue(Object instance, Object value) {
			try {
				field.set(instance, value);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}

	static public class MethodInject implements Injectable {
		private Method method;

		public MethodInject(Method method) {
			this.method = method;
		}

		@Override
		public Type getType() {
			return method.getGenericParameterTypes()[0];
		}

		@Override
		public void setValue(Object instance, Object value) {
			try {
				method.invoke(instance, value);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public Type getType();

	public void setValue(Object instance, Object value);

}
