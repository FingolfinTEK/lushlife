package glassbottle2.extension.jsp;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class JSPModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(JSPInitializer.class);
      binder.clazz(JSPPage.class);
   }

}
