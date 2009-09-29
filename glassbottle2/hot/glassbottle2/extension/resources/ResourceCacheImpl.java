package glassbottle2.extension.resources;

import glassbottle2.binding.StartupTime;
import glassbottle2.util.io.WriteTo;
import glassbottle2.util.loader.ClassLoaderUtil;
import glassbottle2.util.lock.DoubleCheckBlocking;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

@Singleton
public class ResourceCacheImpl implements ResourceCache
{
   static Log logger = Logging.getLog(ResourceCacheImpl.class);

   @Inject
   ClassLoader loader;

   @StartupTime
   Long starupTime;

   private Lock lock = new ReentrantLock();

   @Inject
   Instance<HttpServletResponse> response;

   private Cache cache;

   @PostConstruct
   public void init()
   {
      try
      {
         cache = CacheManager.getInstance().getCacheFactory().createCache(new ConcurrentHashMap());
      }
      catch (CacheException e)
      {
         throw new RuntimeException(e);
      }
   }

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
                  return cache;
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