package negroni;

import java.lang.annotation.Annotation;

/**
 * @author k2.junior.s@gmail.com
 */
public interface Enhancer
{

   /**
    * Gets an instance bound to the given {@link Identifier}
    */
   <T> T getInstance(Identifier<T> id);

   /**
    * Get an instance bound to the given name;
    */
   Object getInstance(String name);

   /**
    * Gets an instance bound to the given class
    */
   <T> T getInstance(Class<T> clazz);

   <T> T getInstance(Class<T> clazz, Annotation bindingType);

   /**
    * Get the initializer used to create this container;
    * 
    */
   Configurator getConfigurator();

   <T> T mixin(final Mix<T> mixin);

   <T> Object call(Mix<T> mixinClass);

}
