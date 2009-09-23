package org.lushlife.negroni.annotation.literal;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;

import org.lushlife.negroni.core.impl.reflection.Reflections;



public abstract class AnnotationLiteral<T extends Annotation> implements
		Annotation {

	protected AnnotationLiteral() {
		if (!(getClass().getSuperclass() == AnnotationLiteral.class)) {
			throw new RuntimeException(
					"Not a direct subclass of AnnotationLiteral");
		}
		if (!(getClass().getGenericSuperclass() instanceof ParameterizedType)) {
			throw new RuntimeException(
					"Missing type parameter in AnnotationLiteral");
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getAnnotationType(Class<?> clazz) {
		ParameterizedType parameterized = (ParameterizedType) clazz
				.getGenericSuperclass();
		return (Class<T>) parameterized.getActualTypeArguments()[0];
	}

	/*
	 * // Alternative impl of getAnnotationType private static <T> Class<T>
	 * getAnnotationType(Class<?> clazz) {
	 * 
	 * Type type = clazz.getGenericSuperclass(); Class<T> annotationType =
	 * null; if (type instanceof ParameterizedType) { ParameterizedType
	 * parameterizedType = (ParameterizedType) type; if
	 * (parameterizedType.getActualTypeArguments().length == 1) { annotationType =
	 * (Class<T>) parameterizedType .getActualTypeArguments()[0]; } } if
	 * (annotationType == null && clazz != Object.class) { return
	 * getAnnotationType(clazz.getSuperclass()); } else { return annotationType; } }
	 * 
	 */

	// TODO: equals(), hashCode()
	private Class<T> annotationType;

	public Class<? extends Annotation> annotationType() {
		annotationType = getAnnotationType(getClass());
		if (annotationType == null) {
			throw new RuntimeException(
					"Unable to determine type of annotation literal for "
							+ getClass());
		}
		return annotationType;
	}

	public int hashCode() {
		return annotationType().hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof Annotation)) {
			return false;
		}
		Annotation annotation = (Annotation) o;
		if (!annotation.annotationType().equals(annotationType)) {
			return false;
		}
		Object[] thisValues = Reflections.annotationValues(this);
		Object[] otherValues = Reflections.annotationValues(annotation);
		for (int i = 0; i < thisValues.length; i++) {
			if (!thisValues[i].equals(otherValues[i])) {
				return false;
			}
		}
		return true;
	}
}
