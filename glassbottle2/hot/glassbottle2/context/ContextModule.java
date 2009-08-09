package glassbottle2.context;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;

public class ContextModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(ContextListener.class);
      binder.model(HiddenContextManager.class);
      binder.model(ServletPathInfo.class);
   }

}
