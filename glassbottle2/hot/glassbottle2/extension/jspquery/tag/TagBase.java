package glassbottle2.extension.jspquery.tag;

import glassbottle2.extension.jspquery.JSPQueryManager;
import glassbottle2.util.lock.DoubleCheckBlocking;
import glassbottle2.util.markup.Markup;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class TagBase<T extends Tag<T>> implements Tag<T>
{
   protected Map<String, Object> attr = new HashMap<String, Object>();
   private JSPQueryManager manager;

   public void setManager(JSPQueryManager manager)
   {
      this.manager = manager;
   }

   @Override
   public T attr(String name, Object value)
   {
      attr.put(name, value);
      return (T) this;
   }

   @Override
   public Object attr(String name)
   {
      return attr.get(name);
   }

   public String toString()
   {
      return start();
   }

   public String start()
   {
      initialization();
      this.manager.addStack(this);
      return (new Markup().start(tag(), attr).toString());
   }

   public String simple()
   {
      initialization();
      return (new Markup().simple(tag(), attr).toString());
   }

   public String end()
   {
      return (new Markup().end(tag()).toString());
   }

   protected void initialization()
   {
   }

   public String tag()
   {
      Class<?> clazz = this.getClass();
      if (this instanceof ProxyObject)
      {
         clazz = clazz.getSuperclass();
      }
      return clazz.getSimpleName().toLowerCase();
   }

   static private Map<Class<?>, Class<?>> cache = new ConcurrentHashMap<Class<?>, Class<?>>();
   static private ReentrantLock lock = new ReentrantLock();

   static public <C extends TagBase<C>> C of(final Class<C> clazz)
   {
      Class<?> proxyClazz = new DoubleCheckBlocking<Class<?>, Class<?>>()
      {

         @Override
         protected Map<Class<?>, Class<?>> cache()
         {
            return cache;
         }

         @Override
         protected Class<?> create()
         {
            return creteProxyClazz(clazz);
         }

         @Override
         protected Class<?> key()
         {
            return clazz;
         }

         @Override
         protected Lock lock()
         {
            return lock;
         }
      }.get();
      try
      {
         return (C) proxyClazz.newInstance();
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }

   }

   private static <C extends TagBase<C>> Class<?> creteProxyClazz(Class<C> clazz)
   {
      ProxyFactory facotyr = new ProxyFactory();
      facotyr.setSuperclass(clazz);
      facotyr.setFilter(new MethodFilter()
      {

         @Override
         public boolean isHandled(Method arg0)
         {
            return Modifier.isAbstract(arg0.getModifiers());
         }
      });
      facotyr.setHandler(new MethodHandler()
      {

         @Override
         public Object invoke(Object arg0, Method arg1, Method arg2, Object[] arg3) throws Throwable
         {
            if (arg3.length == 0)
            {
               return ((C) arg0).attr.get(arg1.getName());
            }
            ((C) arg0).attr(arg1.getName(), arg3[0]);
            return arg0;
         }
      });
      Class<?> proxyClazz = facotyr.createClass();
      return proxyClazz;
   }
}
