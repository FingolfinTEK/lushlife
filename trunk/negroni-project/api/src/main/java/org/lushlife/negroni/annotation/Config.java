package org.lushlife.negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface Config {
	Class<?> type();

	boolean named() default false;

}
