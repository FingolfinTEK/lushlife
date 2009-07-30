package application.lushup;

import application.lushup.model.Customer;
import application.lushup.resources.CustomerResource;
import application.lushup.view.Index;
import glassbottle2.StandardPluginModule;
import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.el.ELModule;
import glassbottle2.extension.groovy.GroovyModule;
import glassbottle2.extension.json.JSONModule;
import glassbottle2.extension.jsp.JSPModule;
import glassbottle2.extension.resources.ResourceModule;
import glassbottle2.extension.yahoo.YahooModule;

public class LushupModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.install(new StandardPluginModule());

      binder.install(new JSONModule());
      // binder.install(new GroovyModule());
      binder.install(new JSPModule());
      binder.install(new YahooModule());
      binder.install(new ELModule());

      binder.clazz(LushupConfiguration.class);
      binder.clazz(CustomerResource.class);
      // binder.clazz(Index.class);
      binder.clazz(Customer.class);

   }

}
