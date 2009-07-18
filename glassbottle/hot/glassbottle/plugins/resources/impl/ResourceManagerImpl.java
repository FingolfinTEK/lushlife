package glassbottle.plugins.resources.impl;


import glassbottle.core.binding.StartupTime;
import glassbottle.plugins.controller.Controller;
import glassbottle.plugins.controller.binding.ActiveController;
import glassbottle.plugins.module.Application;
import glassbottle.plugins.resources.ResourceController;
import glassbottle.plugins.resources.ResourceManager;
import glassbottle.plugins.resources.binding.ResourceContextName;
import glassbottle.util.io.WriteTo;
import glassbottle.util.loader.ClassLoaderUtil;
import glassbottle.util.lock.DoubleCheckBlocking;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.enterprise.inject.Current;
import javax.enterprise.inject.Instance;
import javax.servlet.http.HttpServletResponse;


import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;


@glassbottle.core.scope.Singleton
public class ResourceManagerImpl implements ResourceManager
{
   static Log logger = Logging.getLog(ResourceManagerImpl.class);

   @Current
   ClassLoader loader;

   @Current
   ResourceController resourceController;

   @StartupTime
   Long starupTime;

   private Lock lock = new ReentrantLock();

   @Current
   Instance<glassbottle.plugins.context.ActionContext> context;

   @ActiveController
   Controller controller;

   @Current
   Application application;

   @Current
   Instance<HttpServletResponse> response;

   @ResourceContextName
   String resourceContext;

   private Map<String, byte[]> resources = new ConcurrentHashMap<String, byte[]>();

   @Override
   public WriteTo write(final String contextName, final String type, final String resource)
   {
      return new WriteTo()
      {
         @Override
         public void to(OutputStream stream) throws IOException
         {
            byte[] out = new DoubleCheckBlocking<String, byte[]>()
            {

               @Override
               protected Map<String, byte[]> cache()
               {
                  return resources;
               }

               @Override
               protected byte[] create()
               {
                  try
                  {
                     logger.info("load resources context={0},type={1},resoruce={2}", contextName, type, resource);
                     return ClassLoaderUtil.load(loader, application.resolveController(contextName).getPackage().getName() + "." + type, resource).asBytes();
                  }
                  catch (IOException e)
                  {
                     throw new RuntimeException(e);
                  }
               }

               @Override
               protected String key()
               {
                  return contextName + "#" + type + "#" + resource;
               }

               @Override
               protected Lock lock()
               {
                  return lock;
               }

            }.get();

            stream.write(out);
            writeHeader(type, resource, response.get());
         }
      };
   }

   protected void writeHeader(String type, String resource, HttpServletResponse httpServletResponse)
   {
      if (type.equals("css"))
      {
         httpServletResponse.setContentType("text/css");
      }
      if (type.equals("javascript"))
      {
         httpServletResponse.setContentType("text/javascript");
      }
      if (type.equals("image"))
      {
         String[] strs = resource.split("\\.");
         httpServletResponse.setContentType("image/" + strs[strs.length - 1]);
      }
   }

   @Override
   public String createUrl(String contextName, String type, String resource)
   {
      return "/" + this.context.get().getServletPath() + "/" + resourceContext + "/" + contextName + "/" + type + "/" + resource + "?" + starupTime;
   }
}
