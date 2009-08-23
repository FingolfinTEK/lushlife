package org.slf4j.i18n.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.slf4j.i18n.LogLevel;
import org.slf4j.i18n.spi.LogBindigType;

/**
 * Trace level
 * 
 * @author Takeshi Kondo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@LogBindigType(LogLevel.TRACE)
public @interface Trace {
	String value();
}
