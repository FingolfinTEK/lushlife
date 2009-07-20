package glassbottle2.servlet;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class ServletModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(GlassBottleHttpServletDispatcher.class);
      binder.clazz(ServletManager.class);
   }

}
