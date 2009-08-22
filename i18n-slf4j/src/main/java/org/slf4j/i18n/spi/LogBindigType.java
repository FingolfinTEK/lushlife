package org.slf4j.i18n.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.slf4j.i18n.LogLevel;

/**
 * The LogBindingType bind log level to LogLevel Anntation.
 * 
 * @see org.slf4j.i18n.annotations.Error
 * @see org.slf4j.i18n.annotations.Warn
 * @see org.slf4j.i18n.annotations.Info
 * @see org.slf4j.i18n.annotations.Debug
 * @see org.slf4j.i18n.annotations.Trace
 * @see org.slf4j.i18n.annotations.Message
 * 
 * @author Takeshi Kondo
 * 
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogBindigType {
	LogLevel value();
}
