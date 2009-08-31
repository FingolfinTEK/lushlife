package glassbottle2.scope;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.NormalScope;
import javax.inject.Scope;

@Target( { TYPE, METHOD, FIELD })
@Retention(RUNTIME)
@Documented
@Inherited
@Scope
@NormalScope(passivating = false)
// @ScopeType(normal = false, passivating = false)
public @interface EventScoped
{

}
