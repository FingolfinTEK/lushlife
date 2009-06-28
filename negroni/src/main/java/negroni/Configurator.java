package negroni;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author k2.junior.s@gmail.com
 */
public interface Configurator
{

   Enhancer create();

   <T> Configurator add(Class<T> clazz);

   <T> Configurator configuration(Class<? extends Enum<?>> configuration);

   <T> Configurator configuration(Enum<?> configuration);

   Identifier<?> toId(Type type);

   Identifier<?> toId(Type type, Annotation bindingType);

   Map<String, Identifier<?>> getNameMapping();
}
