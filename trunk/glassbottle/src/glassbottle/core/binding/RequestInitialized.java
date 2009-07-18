package glassbottle.core.binding;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.BindingType;

@BindingType
@Retention(RUNTIME)
@Target( { FIELD, PARAMETER })
public @interface RequestInitialized
{

}
