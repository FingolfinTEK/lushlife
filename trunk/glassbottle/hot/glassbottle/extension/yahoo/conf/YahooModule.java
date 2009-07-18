package glassbottle.extension.yahoo.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.extension.yahoo.YahooController;

public class YahooModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(YahooConfigurator.class);
      binder.clazz(YahooController.class);
   }

}
