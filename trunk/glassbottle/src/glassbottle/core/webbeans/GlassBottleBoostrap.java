package glassbottle.core.webbeans;


import glassbottle.GlassBottle;
import glassbottle.core.ClassLoaderProducer;
import glassbottle.core.dsl.WebBeansModule;

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
      Bootstrap bootstrap = new WebBeansBootstrap();
      bootstrap.setEnvironment(Environments.SERVLET);
      bootstrap.getServices().add(Deployment.class, new GlassBottleDeployment(new GlassBottleBeanDeploymentArchive(module)));
      bootstrap.setApplicationContext(new ConcurrentHashMapBeanStore());
      bootstrap.initialize();
      bootstrap.boot();
      DependentContext.instance().setActive(true);
      GlassBottle.setBootStrap(bootstrap);
      BeanManager manager = bootstrap.getManager();
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
