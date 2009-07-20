package glassbottle2.extension.yahoo;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class YahooModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(YahooResource.class);
   }

}
