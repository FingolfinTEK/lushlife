package glassbottle2.bootstrap;

import glassbottle2.WebBeansModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

public class ModuleLoader
{

   static Log log = Logging.getLog(ModuleLoader.class);

   static public List<WebBeansModule> loadModules(ClassLoader loader)
   {
      try
      {
         ArrayList<WebBeansModule> modules = new ArrayList<WebBeansModule>();
         Enumeration<URL> urls = loader.getResources("META-INF/glassbottle.modules");
         HashSet<String> classNames = loadModulesClasses(urls);
         for (String s : classNames)
         {
            try
            {
               if (log.isInfoEnabled())
               {
                  log.info("load " + loader.getResource(s.replace(".", "/") + ".class"));
               }
               modules.add((WebBeansModule) loader.loadClass(s).newInstance());
               if (log.isInfoEnabled())
               {
                  log.info("load " + s);
               }
            }
            catch (InstantiationException e)
            {
               log.warn("instantinate failed " + s, e);
            }
            catch (IllegalAccessException e)
            {
               log.warn("access failed " + s, e);
            }
            catch (ClassNotFoundException e)
            {
               log.warn("load failed " + s, e);
            }
         }
         return modules;
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   private static HashSet<String> loadModulesClasses(Enumeration<URL> urls) throws IOException
   {
      HashSet<String> classNames = new HashSet<String>();
      while (urls.hasMoreElements())
      {
         URL url = urls.nextElement();
         if (log.isInfoEnabled())
         {
            log.info("lush.modules file " + url);
         }
         InputStream stream = url.openStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
         try
         {
            for (String s = reader.readLine(); s != null; s = reader.readLine())
            {
               String className = s.trim();
               if (className.length() == 0)
               {
                  continue;
               }
               classNames.add(s);
            }
         }
         finally
         {
            reader.close();
         }
      }
      return classNames;
   }
}
