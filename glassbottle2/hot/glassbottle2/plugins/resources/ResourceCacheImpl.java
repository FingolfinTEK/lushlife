package glassbottle2.plugins.resources;

import glassbottle2.binding.StartupTime;
import glassbottle2.scope.Singleton;
import glassbottle2.util.io.WriteTo;
import glassbottle2.util.loader.ClassLoaderUtil;
import glassbottle2.util.lock.DoubleCheckBlocking;

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

@Singleton
public class ResourceCacheImpl implements ResourceCache
{
   static Log logger = Logging.getLog(ResourceCacheImpl.class);

   @Current
   ClassLoader loader;

   @StartupTime
   Long starupTime;

   private Lock lock = new ReentrantLock();

   @Current
   Instance<HttpServletResponse> response;

   private Map<String, byte[]> resources = new ConcurrentHashMap<String, byte[]>();

   @Override
   public WriteTo write(final String resource)
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
                     return ClassLoaderUtil.load(loader, resource).asBytes();
                  }
                  catch (IOException e)
                  {
                     throw new RuntimeException(e);
                  }
               }

               @Override
               protected String key()
               {
                  return resource;
               }

               @Override
               protected Lock lock()
               {
                  return lock;
               }

            }.get();
            stream.write(out);
         }
      };
   }

}
