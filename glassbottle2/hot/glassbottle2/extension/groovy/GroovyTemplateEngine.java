package glassbottle2.extension.groovy;

import glassbottle2.binding.Encoding;
import glassbottle2.scope.Singleton;
import glassbottle2.util.lock.DoubleCheckBlocking;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.enterprise.inject.Current;
import javax.enterprise.inject.Initializer;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

@Singleton
public class GroovyTemplateEngine
{

   Log logger = Logging.getLog(GroovyTemplateEngine.class);

   private TemplateEngine engine;

   @Current
   private ClassLoader loader;

   @Encoding
   private String encoding;

   private ConcurrentHashMap<String, Template> cache = new ConcurrentHashMap<String, Template>();
   private Lock lock = new ReentrantLock();

   @Initializer
   public GroovyTemplateEngine(GroovyShellManager manager)
   {
      this.engine = new SimpleTemplateEngine(manager.getShell());
   }

   public Template create(final String classLoaderPath)
   {
      return new DoubleCheckBlocking<String, Template>()
      {

         @Override
         protected Map<String, Template> cache()
         {
            return cache;
         }

         @Override
         protected Template create()
         {
            URL url = loader.getResource(classLoaderPath);
            if (url == null)
            {
               throw new IllegalStateException("gsp not found " + classLoaderPath);
            }
            try
            {
               // Reader reader = new InputStreamReader(url.openStream(),
               // encoding);
               try
               {
                  logger.info("gsp {0}", url);
                  return engine.createTemplate(url);
                  // if (logger.isDebugEnabled())
                  // {
                  // BufferedReader br = new BufferedReader(reader);
                  // StringBuilder sb = new StringBuilder();
                  // for (String s = br.readLine(); s != null; s =
                  // br.readLine())
                  // {
                  // sb.append(s);
                  // sb.append("\n");
                  // }
                  // logger.debug(sb.toString());
                  // return engine.createTemplate(sb.toString());
                  // }
                  // else
                  // {
                  // return engine.
                  // return engine.createTemplate(new
                  // InputStreamReader(reader));
                  // }
               }
               finally
               {
                  // reader.close();
               }
            }
            catch (Exception e)
            {
               throw new RuntimeException(e);
            }
         }

         @Override
         protected String key()
         {
            return classLoaderPath;
         }

         @Override
         protected Lock lock()
         {
            return lock;
         }

      }.get();

   }
}
