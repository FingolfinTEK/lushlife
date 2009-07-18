package glassbottle.plugins.context.scope;


import glassbottle.core.context.LushContext;

import java.util.Map;

import javax.enterprise.context.spi.Contextual;


import org.jboss.webbeans.context.AbstractMapContext;
import org.jboss.webbeans.context.api.BeanInstance;
import org.jboss.webbeans.context.api.BeanStore;
import org.jboss.webbeans.context.api.helpers.AbstractMapBackedBeanStore;


public class HiddenContext extends AbstractMapContext
{

   //
   // @Override
   // public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped)
   // {
   // return new Provider<T>()
   // {
   // @SuppressWarnings("unchecked")
   // @Override
   // public T get()
   // {
   // Map<String, Object> map = LushContext.getHiddenScope();
   // Object obj = map.get(key.toString());
   // if (obj == null)
   // {
   // obj = unscoped.get();
   // map.put(key.toString(), obj);
   // }
   // return (T) obj;
   // }
   //
   // };
   // }

   static public HiddenContext INSTANCE = new HiddenContext();

   protected HiddenContext()
   {
      super(HiddenScoped.class);
   }

   @Override
   protected BeanStore getBeanStore()
   {

      return new AbstractMapBackedBeanStore()
      {

         @Override
         public Map<Contextual<? extends Object>, BeanInstance<? extends Object>> delegate()
         {
            return LushContext.getHiddenScope();
         }

      };
   }

   @Override
   protected boolean isCreationLockRequired()
   {
      return false;
   }

}
