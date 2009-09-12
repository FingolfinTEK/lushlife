package stlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import stlog.spi.LogLevelBinding;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@LogLevelBinding(Level.TRACE)
public @interface Trace {
	String value();
}
