package glassbottle2.servlet;

import glassbottle2.GlassBottle;
import glassbottle2.GlassBottleContext;
import glassbottle2.binding.RequestDestroyedLiteral;
import glassbottle2.binding.RequestInitializedLiteral;
import glassbottle2.bootstrap.GlassBottleBoostrap;
import glassbottle2.util.loader.ClassLoaderProducer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.jboss.webbeans.CurrentManager;
import org.jboss.webbeans.context.DependentContext;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.servlet.WebBeansListener;

public class GlassBottleListener extends WebBeansListener implements ServletRequestListener, ServletContextListener
{

   static Log logger = org.jboss.webbeans.log.Logging.getLog(GlassBottleListener.class);

   @Override
   public void contextDestroyed(ServletContextEvent event)
   {
      glassbottle2.GlassBottleContext.getHiddenScope().clear();
      GlassBottleContext.setServletContext(event.getServletContext());
      ClassLoaderProducer.produceClassLoader(event.getServletContext());
      try
      {
         GlassBottleBoostrap.destoryManager();
      }
      finally
      {
         GlassBottleContext.setServletContext(null);
         GlassBottleContext.getHiddenScope().clear();
      }
      super.contextDestroyed(event);
   }

   @Override
   public void contextInitialized(ServletContextEvent event)
   {
      GlassBottleContext.getHiddenScope().clear();
      GlassBottleContext.setServletContext(event.getServletContext());
      ClassLoaderProducer.produceClassLoader(event.getServletContext());
      try
      {
         GlassBottleBoostrap.initManager();
      }
      finally
      {
         GlassBottleContext.setServletContext(null);
         GlassBottleContext.getHiddenScope().clear();
      }
      super.contextInitialized(event);
      CurrentManager.rootManager().fireEvent(event, new RequestInitializedLiteral());
   }

   @Override
   public void requestDestroyed(ServletRequestEvent event)
   {
      CurrentManager.rootManager().fireEvent(event, new RequestDestroyedLiteral());
      GlassBottleContext.setServletContext(null);
      GlassBottleContext.setRequest(null);
      GlassBottleContext.setResponse(null);
      GlassBottleContext.getHiddenScope().clear();
      super.requestDestroyed(event);

   }

   @Override
   public void requestInitialized(ServletRequestEvent event)
   {
      GlassBottleContext.setServletContext(event.getServletContext());
      GlassBottleContext.setRequest((HttpServletRequest) event.getServletRequest());
      GlassBottleContext.getHiddenScope().clear();
      boolean update = isUpdate();
      logger.info("hot deploy[{0}] , update[{1}]", GlassBottle.isHotdeployMode(), update);

      if (GlassBottle.isHotdeployMode())
      {
         if (update)
         {
            ClassLoaderProducer.produceClassLoader(event.getServletContext());
         }
         Thread.currentThread().setContextClassLoader(ClassLoaderProducer.getClassLoader());
         if (update)
         {
            logger.info("init bean manager");
            GlassBottleBoostrap.initManager();
         }
      }
      GlassBottle.getServiceRegistry().get(DependentContext.class).setActive(true);
      CurrentManager.rootManager().fireEvent(event, new RequestInitializedLiteral());
      super.requestInitialized(event);
   }

   static public final String LAST_UPDATE_TIME = "LUSH_LAST_UPDATE";

   private boolean isUpdate()
   {
      ServletContext context = GlassBottleContext.getServletContext();
      Long lastUpdate = (Long) context.getAttribute(LAST_UPDATE_TIME);
      if (lastUpdate == null)
      {
         return false;
      }
      long time = lastUpdate;
      try
      {
         URL url = context.getResource("/WEB-INF/modules/");
         if (url == null)
         {
            return false;
         }
         if (url.toString().startsWith("file"))
         {
            File file = new File(url.getFile());
            LinkedList<File> files = new LinkedList<File>();
            files.add(file);
            while (!files.isEmpty())
            {
               File target = files.pollLast();
               if (time < target.lastModified())
               {
                  return true;
               }
               if (target.isDirectory())
               {
                  for (File f : target.listFiles())
                  {
                     files.addLast(f);
                  }
               }
            }
         }
         return false;
      }
      catch (MalformedURLException e)
      {
         throw new RuntimeException(e);
      }
   }
}
