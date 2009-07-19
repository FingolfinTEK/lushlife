package glassbottle2.application.lushup;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.application.lushup.model.Customer;
import glassbottle2.application.lushup.resources.CustomerResource;
import glassbottle2.application.lushup.view.Index;
import glassbottle2.extension.groovy.GroovyModule;
import glassbottle2.extension.json.JSONModule;
import glassbottle2.plugins.StandardPluginModule;

public class LushupModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {

      binder.install(new StandardPluginModule());

      binder.install(new JSONModule());
      binder.install(new GroovyModule());

      binder.clazz(LushupConfiguration.class);
      binder.clazz(CustomerResource.class);
      binder.clazz(Index.class);
      binder.clazz(Customer.class);

   }

}
