package glassbottle2;

import javax.enterprise.inject.spi.Bean;
import javax.servlet.ServletContext;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.CurrentManager;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
import org.jboss.webbeans.bootstrap.api.ServiceRegistry;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

public class GlassBottle
{

   static Log log = Logging.getLog(GlassBottle.class);

   public static boolean isHotdeployMode()
   {
      ServletContext context = glassbottle2.GlassBottleContext.getServletContext();
      String str = context.getServerInfo();
      if (!str.contains("Development"))
      {
         return false;
      }
      return true;
   }

   static public final String SERVICE_REGISTRY = ServiceRegistry.class.getName();
   static public final String BOOTSTRAP = Bootstrap.class.getName();

   static public void setServiceRegistry(ServiceRegistry bootstrap)
   {
      ServletContext contdxt = GlassBottleContext.getServletContext();
      if (contdxt == null)
      {
         return;
      }
      contdxt.setAttribute(SERVICE_REGISTRY, bootstrap);
   }

   static public void setBootstrap(Bootstrap bootstrap)
   {
      ServletContext contxt = GlassBottleContext.getServletContext();
      contxt.setAttribute(BOOTSTRAP, bootstrap);
   }

   static public Bootstrap getBootstrap()
   {
      ServletContext contxt = GlassBottleContext.getServletContext();
      return (Bootstrap) contxt.getAttribute(BOOTSTRAP);
   }

   static public ServiceRegistry getServiceRegistry()
   {
      ServletContext contxt = GlassBottleContext.getServletContext();
      return getServiceRegistry(contxt);
   }

   public static ServiceRegistry getServiceRegistry(ServletContext contxt)
   {
      if (contxt == null)
      {
         return null;
      }
      return (ServiceRegistry) contxt.getAttribute(SERVICE_REGISTRY);
   }

   static public Injector getInjector()
   {
      BeanManagerImpl beanManager = CurrentManager.rootManager();
      if (log.isInfoEnabled())
      {
         log.info("CurrentManager[{0}] {1}", beanManager.hashCode(), beanManager);
      }
      Bean<?> injectorBean = beanManager.getBeans(Injector.class).iterator().next();
      return (Injector) beanManager.getReference(injectorBean, beanManager.createCreationalContext(injectorBean));
   }
}
