package glassbottle2;

import javax.enterprise.inject.spi.Bean;
import javax.servlet.ServletContext;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.CurrentManager;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
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

   static public final String BOOTSTRAP = Bootstrap.class.getName();

   static public void setBootStrap(Bootstrap bootstrap)
   {
      ServletContext contdxt = GlassBottleContext.getServletContext();
      if (contdxt == null)
      {
         return;
      }
      contdxt.setAttribute(BOOTSTRAP, bootstrap);
   }

   static public Bootstrap getBootStrap()
   {
      ServletContext contxt = GlassBottleContext.getServletContext();
      return getBootStrap(contxt);
   }

   public static Bootstrap getBootStrap(ServletContext contxt)
   {
      if (contxt == null)
      {
         return null;
      }
      return (Bootstrap) contxt.getAttribute(BOOTSTRAP);
   }

   static public Injector getInjector()
   {
      BeanManagerImpl beanManager = CurrentManager.rootManager();
      Bean<?> injectorBean = beanManager.getBeans(Injector.class).iterator().next();
      return (Injector) beanManager.getReference(injectorBean, beanManager.createCreationalContext(injectorBean));
   }
}
