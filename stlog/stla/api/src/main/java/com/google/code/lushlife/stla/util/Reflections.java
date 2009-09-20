package com.google.code.lushlife.stla.util;

import java.lang.annotation.Annotation;


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
			LogLog.reportFailure("Failed to get value " + annotation, e1);
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
			LogLog.reportFailure("Failed to get annotations  " + e, e1);
		}
		return null;
	}

}
