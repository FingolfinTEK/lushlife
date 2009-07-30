package glassbottle2.extension.yahoo;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.extension.resources.ResourceModule;

public class YahooModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(YahooResource.class);
      binder.install(new ResourceModule());
   }

}
