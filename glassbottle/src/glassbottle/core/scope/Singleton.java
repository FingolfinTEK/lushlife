package glassbottle.core.scope;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.ScopeType;

@Target( { TYPE, METHOD, FIELD })
@Retention(RUNTIME)
@Documented
@Inherited
@ScopeType(normal = false, passivating = false)
public @interface Singleton
{

}
