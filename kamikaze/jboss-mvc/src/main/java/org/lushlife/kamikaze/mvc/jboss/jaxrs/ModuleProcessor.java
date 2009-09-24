package org.lushlife.kamikaze.mvc.jboss.jaxrs;


import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;
import org.lushlife.kamikaze.Kamikaze;


public class ModuleProcessor
{

   private final Registry registry;
   private final ResteasyProviderFactory providerFactory;

   public ModuleProcessor(final Registry registry, final ResteasyProviderFactory providerFactory)
   {
      this.registry = registry;
      this.providerFactory = providerFactory;
   }

   public void process(final Iterable<Class<?>> modules)
   {
      processInjector(modules);
   }

   private void processInjector(Iterable<Class<?>> classes)
   {
      for (final Class<?> clazz : classes)
      {
         final Class<?> beanClass = (Class) clazz;
         if (GetRestful.isRootResource(beanClass))
         {
            final ResourceFactory resourceFactory = new JBossKamikazeResourceFactory(beanClass);
            registry.addResourceFactory(resourceFactory);
         }
         if (beanClass.isAnnotationPresent(Provider.class))
         {
            providerFactory.registerProviderInstance(Kamikaze.getInjector().getInstance(clazz));
         }
      }
   }

}
