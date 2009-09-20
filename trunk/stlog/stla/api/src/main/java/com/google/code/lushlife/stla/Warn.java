package com.google.code.lushlife.stla;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.code.lushlife.stla.spi.LogLevelBinding;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@LogLevelBinding(Level.WARN)
public @interface Warn {
	String value();
}
