package glassbottle.application.lushup.conf;

import glassbottle.application.lushup.LushupController;
import glassbottle.application.lushup.action.Lushup;
import glassbottle.application.lushup.view.Index;
import glassbottle.application.lushup.view.Test;
import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.extension.google.conf.GoogleModule;
import glassbottle.extension.yahoo.conf.YahooModule;
import glassbottle.plugins.StandardPluginModule;

public class LushupModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {

      binder.install(new StandardPluginModule());

      binder.install(new GoogleModule());
      binder.install(new YahooModule());

      binder.clazz(LushupApplicationConfiguration.class);
      binder.clazz(LushupController.class);

      binder.clazz(Lushup.class);

      binder.clazz(Index.class);
      binder.clazz(Test.class);
   }

}
