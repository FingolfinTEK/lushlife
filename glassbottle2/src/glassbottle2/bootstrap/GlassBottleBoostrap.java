package glassbottle2.bootstrap;

import glassbottle2.GlassBottle;
import glassbottle2.WebBeansModule;
import glassbottle2.scope.SingletonContext;
import glassbottle2.util.loader.ClassLoaderProducer;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.spi.BeanManager;

import org.jboss.webbeans.bootstrap.WebBeansBootstrap;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
import org.jboss.webbeans.bootstrap.api.Environments;
import org.jboss.webbeans.bootstrap.spi.Deployment;
import org.jboss.webbeans.context.DependentContext;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;

public class GlassBottleBoostrap
{

   static public void initManager()
   {
      List<WebBeansModule> modules = ModuleLoader.loadModules(ClassLoaderProducer.getClassLoader());
      GlassBottleBoostrap.initManager(modules);
   }

   static public void initManager(WebBeansModule... module)
   {
      initManager(Arrays.asList(module));
   }

   static public void initManager(Iterable<WebBeansModule> module)
   {
      destoryManager();
      Bootstrap bootstrap = new WebBeansBootstrap()
      {

         @Override
         protected void initializeContexts()
         {
            super.initializeContexts();
            getManager().addContext(getServices().get(SingletonContext.class));
         }

         @Override
         protected void createContexts()
         {
            super.createContexts();
            getServices().add(SingletonContext.class, new SingletonContext());
         }

      };
      bootstrap.setEnvironment(Environments.SERVLET);
      GlassBottleDeployment deployment = new GlassBottleDeployment(new GlassBottleBeanDeploymentArchive(module));
      bootstrap.getServices().add(Deployment.class, deployment);
      bootstrap.setApplicationContext(new ConcurrentHashMapBeanStore());
      bootstrap.initialize();
      bootstrap.boot();
      bootstrap.getServices().get(DependentContext.class).setActive(true);
      GlassBottle.setBootStrap(bootstrap);
      BeanManager manager = bootstrap.getManager();
      manager.fireEvent(deployment);
   }

   static public void destoryManager()
   {
      Bootstrap bootStrap = GlassBottle.getBootStrap();
      if (bootStrap != null)
      {
         bootStrap.shutdown();
      }
   }
}
