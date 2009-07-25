package glassbottle2.extension.jaxrs;

import glassbottle2.GlassBottle;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;

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
            final ResourceFactory resourceFactory = new GlassBottleResourceFactory(beanClass);
            registry.addResourceFactory(resourceFactory);
         }
         if (beanClass.isAnnotationPresent(Provider.class))
         {
            providerFactory.registerProviderInstance(GlassBottle.getInjector().getInstanceByType(clazz));
         }
      }
   }

}
