package stlog.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import stlog.Level;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@LogLevelBinding(Level.INFO)
public @interface Info {
	String value();
}
