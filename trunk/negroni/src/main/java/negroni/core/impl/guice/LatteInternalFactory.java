package negroni.core.impl.guice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.PostConstruct;

import negroni.Enhancer;
import negroni.annotation.literal.Configuration;
import negroni.core.impl.ConfiguratorBase;
import negroni.core.impl.delegate.DelegateMethod;
import negroni.core.impl.inject.InjectionUnit;
import negroni.core.impl.reflection.MethodId;
import negroni.core.impl.reflection.ProxyFactory;
import negroni.core.impl.reflection.Reflections;

import com.google.inject.Injector;
import com.google.inject.InternalContextPublic;
import com.google.inject.InternalFactoryPublic;

class LatteInternalFactory<T> extends InternalFactoryPublic<T>
{
   Class<T> clazz;
   HashMap<MethodId, DelegateMethod> mapping;
   ConfiguratorBase configurator;
   Set<Method> postCreate;
   Configuration binding;

   @SuppressWarnings("unchecked")
   public LatteInternalFactory(ConfiguratorBase configurator, Configuration binding)
   {
      this.clazz = (Class<T>) binding.type();
      this.configurator = configurator;
      this.mapping = Reflections.createMapping(clazz, clazz, configurator);
      this.postCreate = Reflections.getMethodsPresentByAnnotations(clazz, PostConstruct.class);
      this.binding = binding;
   }

   @Override
   public T get(InternalContextPublic context)
   {
      Injector injector = context.getInjectorImpl();
      Enhancer container = new GuiceEnhancer(injector, configurator);
      T obj = ProxyFactory.createProxy(clazz, container, mapping);
      for (InjectionUnit<?> injectionUnit : binding.getInjectionUnits())
      {
         injectionUnit.inject(obj, container);
      }
      for (Method m : postCreate)
      {
         Reflections.invoke(obj, m, new Object[] {});
      }
      return obj;
   }
}
