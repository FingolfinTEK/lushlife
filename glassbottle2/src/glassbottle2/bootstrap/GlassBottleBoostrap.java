package glassbottle2.bootstrap;

import glassbottle2.BeanModule;
import glassbottle2.GlassBottle;
import glassbottle2.scope.EventContext;
import glassbottle2.scope.SingletonContext;
import glassbottle2.util.loader.ClassLoaderProducer;

import java.util.Arrays;

import javax.servlet.ServletContext;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.CurrentManager;
import org.jboss.webbeans.bootstrap.WebBeansBootstrap;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
import org.jboss.webbeans.bootstrap.api.Environments;
import org.jboss.webbeans.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.context.DependentContext;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;
import org.jboss.webbeans.servlet.api.ServletServices;

public class GlassBottleBoostrap
{
   static public Log logger = Logging.getLog(GlassBottleBoostrap.class);

   static public void initManager()
   {
      Iterable<BeanModule> modules = ModuleLoader.loadModules(ClassLoaderProducer.getClassLoader());
      if (logger.isDebugEnabled())
      {
         for (BeanModule module : modules)
         {
            logger.debug("load bean module [{0}]", module.getClass());
         }
      }
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

      GlassBottleDeployment deployment = new GlassBottleDeployment(archive);
      service.add(ServletServices.class, new ServletServices()
      {
         @Override
         public BeanDeploymentArchive getBeanDeploymentArchive(ServletContext ctx)
         {
            return archive;
         }
      });
      bootstrap.startContainer(Environments.SERVLET, deployment, new ConcurrentHashMapBeanStore());
      bootstrap.startInitialization().deployBeans().validateBeans().endInitialization();

      service.get(DependentContext.class).setActive(true);
      GlassBottle.setBootstrap(bootstrap);

      GlassBottle.setServiceRegistry(service);
      BeanManagerImpl manager = (BeanManagerImpl) bootstrap.getManager(archive);

      CurrentManager.setRootManager(manager);
      manager.addContext(new SingletonContext());
      manager.addContext(EventContext.INSTANCE);
      manager.fireEvent(deployment);
   }

   static public void destoryManager()
   {
      Bootstrap bootStrap = GlassBottle.getBootstrap();
      if (bootStrap != null)
      {
         bootStrap.shutdown();
      }
   }
}
