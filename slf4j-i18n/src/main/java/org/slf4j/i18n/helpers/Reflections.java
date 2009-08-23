package org.slf4j.i18n.helpers;

import java.lang.annotation.Annotation;

import org.slf4j.helpers.Util;
import org.slf4j.i18n.spi.LogBindigType;

/**
 * Reflection helper class
 * 
 * @author Takeshi Kondo
 */
public class Reflections {

	/**
	 * 
	 * @param annotation
	 * @return
	 */
	static public Object getValue(Annotation annotation) {
		try {
			return annotation.annotationType().getMethod("value").invoke(
					annotation);
		} catch (Exception e1) {
			Util.reportFailure("Failed to get value " + annotation, e1);
		}
		return null;
	}

	/**
	 * return annotations associated with enums
	 */
	static public Annotation[] getAnnotations(Enum<?> e) {
		try {
			return e.getDeclaringClass().getDeclaredField(e.name())
					.getAnnotations();
		} catch (Exception e1) {
			Util.reportFailure("Failed to get annotations  " + e, e1);
		}
		return null;
	}

	static public Annotation getLogLevelAnnotation(Enum<?> e) {
		for (Annotation a : getAnnotations(e)) {
			if (a.annotationType().isAnnotationPresent(LogBindigType.class)) {
				return a;
			}
		}
		return null;
	}

}
