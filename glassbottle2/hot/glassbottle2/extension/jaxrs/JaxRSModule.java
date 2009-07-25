package glassbottle2.extension.jaxrs;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class JaxRSModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(JaxRSInitializer.class);
      binder.clazz(JaxRsServletDispatcher.class);
   }

}
