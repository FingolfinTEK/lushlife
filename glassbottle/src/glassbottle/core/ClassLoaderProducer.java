package glassbottle.core;


import glassbottle.core.context.LushContext;
import glassbottle.servlet.GlassBottleListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;


import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;


public class ClassLoaderProducer
{

   static public void produceClassLoader(ServletContext context)
   {
      URL url;
      try
      {
         url = context.getResource("/WEB-INF/modules/");
      }
      catch (MalformedURLException e)
      {
         throw new IllegalStateException(e);
      }
      if (url != null)
      {
         produceClassLoader(context, url);
      }
   }

   static Log logger = Logging.getLog(ClassLoaderProducer.class);

   private static void produceClassLoader(ServletContext context, URL... urls)
   {
      if (logger.isInfoEnabled())
      {
         logger.info("hot deploy classloader create  " + Arrays.toString(urls));
      }
      ClassLoader loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
      context.setAttribute(ClassLoaderProducer.class.getName(), loader);
      context.setAttribute(GlassBottleListener.LAST_UPDATE_TIME, System.currentTimeMillis());
   }

   @Produces
   static public ClassLoader getClassLoader()
   {
      ServletContext context = LushContext.getServletContext();
      if (context == null)
      {
         return Thread.currentThread().getContextClassLoader();
      }
      return getClassLoader(context);
   }

   static public ClassLoader getClassLoader(ServletContext context)
   {
      ClassLoader loader = (ClassLoader) context.getAttribute(ClassLoaderProducer.class.getName());
      if (loader == null)
      {
         return Thread.currentThread().getContextClassLoader();
      }
      return loader;
   }

}
