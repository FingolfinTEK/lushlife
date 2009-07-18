package glassbottle.plugins.context.impl;


import glassbottle.plugins.context.NamingResolver;
import glassbottle.plugins.controller.Controller;
import glassbottle.plugins.controller.binding.ActiveController;
import glassbottle.util.beans.BeansUtil;

import java.util.Set;

import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;


import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;


public class NamingResolverImpl implements NamingResolver
{
   Log log = Logging.getLog(NamingResolverImpl.class);
   @Current
   BeanManager manager;

   @ActiveController
   Instance<Controller> controller;

   @Current
   ClassLoader loader;

   @Override
   public Bean<?> resolve(String name)
   {
      Bean<?> bean = null;
      if (bean == null)
      {
         bean = resolveByBeanManager(name);
      }

      if (bean == null)
      {
         bean = resolveByControllerPackage(name);
      }
      return bean;
   }

   private Bean<?> resolveByControllerPackage(String name)
   {
      String clazzName = BeansUtil.toClassName(name);
      String fullName = controller.get().getPackage().getName() + ".model." + clazzName;
      try
      {
         Class<?> clazz = loader.loadClass(fullName);
         Set<Bean<?>> beans = manager.getBeans(clazz);
         return validate(clazz.getName(), beans);
      }
      catch (ClassNotFoundException e)
      {
         log.debug("class not found {0}", e, fullName);
         return null;
      }

   }

   private Bean<?> resolveByBeanManager(String name)
   {
      Set<Bean<?>> beans = manager.getBeans(name);
      return validate(name, beans);
   }

   private Bean<?> validate(String name, Set<Bean<?>> beans)
   {
      if (beans.size() == 0)
      {
         return null;
      }
      if (beans.size() > 1)
      {
         throw new AmbiguousResolutionException(name + " Resolved multiple Web Beans " + beans);
      }
      return beans.iterator().next();
   }

}
