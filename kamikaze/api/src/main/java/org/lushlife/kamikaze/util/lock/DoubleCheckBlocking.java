package org.lushlife.kamikaze.util.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;

public abstract class DoubleCheckBlocking<K, V>
{

   abstract protected V create();

   abstract protected Map<K, V> cache();

   abstract protected K key();

   abstract protected Lock lock();

   public V get()
   {
      K key = key();
      V value = cache().get(key);
      if (value == null)
      {
         Lock lock = lock();
         lock.lock();
         try
         {
            value = cache().get(key);
            if (value == null)
            {
               value = create();
               cache().put(key, value);
            }
         }
         finally
         {
            lock.unlock();
         }
      }
      return value;
   }

}
