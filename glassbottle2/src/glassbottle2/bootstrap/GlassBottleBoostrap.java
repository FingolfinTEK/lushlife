package glassbottle2.bootstrap;

import glassbottle2.BeanModule;
import glassbottle2.GlassBottle;
import glassbottle2.util.loader.ClassLoaderProducer;

import java.util.Arrays;

import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContext;

import org.jboss.webbeans.bootstrap.WebBeansBootstrap;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
import org.jboss.webbeans.bootstrap.api.Environments;
import org.jboss.webbeans.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.context.DependentContext;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.jboss.webbeans.servlet.api.ServletServices;

public class GlassBottleBoostrap
{

   static public void initManager()
   {
      Iterable<BeanModule> modules = ModuleLoader.loadModules(ClassLoaderProducer.getClassLoader());
      GlassBottleBoostrap.initManager(modules);
   }

   static public void initManager(BeanModule... module)
   {
      initManager(Arrays.asList(module));
   }

   static public void initManager(Iterable<BeanModule> module)
   {
      destoryManager();
      final SimpleServiceRegistry service = new SimpleServiceRegistry();
      final BeanModuleBeanDeploymentArchive archive = new BeanModuleBeanDeploymentArchive(service, module);
      Bootstrap bootstrap = new WebBeansBootstrap()
      {

         @Override
         protected void initializeContexts()
         {
            super.initializeContexts();
         }

         @Override
         protected void createContexts()
         {
            super.createContexts();
         }

      };

      // bootstrap.setEnvironment();
      GlassBottleDeployment deployment = new GlassBottleDeployment(archive);
      // bootstrap.setApplicationContext();
      service.add(ServletServices.class, new ServletServices()
      {
         @Override
         public BeanDeploymentArchive getBeanDeploymentArchive(ServletContext ctx)
         {
            return archive;
         }
      });
      bootstrap.startContainer(Environments.SERVLET, deployment, new ConcurrentHashMapBeanStore());
      bootstrap.deployBeans();
      bootstrap.startInitialization();

      service.get(DependentContext.class).setActive(true);
      GlassBottle.setBootStrap(bootstrap);
      BeanManager manager = bootstrap.getManager(archive);
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
