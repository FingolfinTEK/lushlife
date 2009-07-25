package glassbottle2.context;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class ContextModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ContextListener.class);
      binder.clazz(HiddenContextManager.class);
      binder.clazz(ServletPathInfo.class);
   }

}
