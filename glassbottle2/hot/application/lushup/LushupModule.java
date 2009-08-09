package application.lushup;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.StandardPluginModule;
import glassbottle2.el.ELModule;
import glassbottle2.extension.jquery.JQueryModule;
import glassbottle2.extension.json.JSONModule;
import glassbottle2.extension.jsp.JSPModule;
import glassbottle2.extension.yahoo.YahooModule;
import application.lushup.model.Customer;
import application.lushup.resources.CustomerResource;

public class LushupModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.install(new StandardPluginModule());

      binder.install(new JSONModule());
      // binder.install(new GroovyModule());
      binder.install(new JSPModule());
      binder.install(new YahooModule());
      binder.install(new ELModule());
      binder.install(new JQueryModule());

      binder.model(LushupConfiguration.class);
      binder.model(CustomerResource.class);
      binder.model(Customer.class);
   }

}
