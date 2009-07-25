package glassbottle2.el.impl;

import glassbottle2.el.NamingResolver;

import java.util.Set;

import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

public class NamingResolverImpl implements NamingResolver
{
   Log log = Logging.getLog(NamingResolverImpl.class);
   @Current
   BeanManager manager;

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

      return bean;
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
