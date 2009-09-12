package stla;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import stla.spi.LogLevelBinding;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@LogLevelBinding(Level.ERROR)
public @interface Error {
	String value();
}
