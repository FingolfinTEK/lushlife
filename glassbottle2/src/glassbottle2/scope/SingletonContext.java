package glassbottle2.scope;

import org.jboss.webbeans.bootstrap.api.Service;
import org.jboss.webbeans.context.AbstractMapContext;
import org.jboss.webbeans.context.api.BeanStore;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;

public class SingletonContext extends AbstractMapContext implements Service
{

   public SingletonContext()
   {
      super(Singleton.class);
   }

   BeanStore beanStore = new ConcurrentHashMapBeanStore();

   @Override
   protected BeanStore getBeanStore()
   {
      return beanStore;
   }

   @Override
   public boolean isActive()
   {
      return true;
   }

   @Override
   protected boolean isCreationLockRequired()
   {
      return true;
   }

}
