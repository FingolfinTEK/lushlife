package application.lushup;

import org.lushlife.kamikaze.CoreModule;
import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.extension.jquery.JQueryModule;
import org.lushlife.kamikaze.extension.yahoo.YahooModule;
import org.lushlife.kamikaze.json.JSONModule;
import org.lushlife.kamikaze.jsp.JSPModule;

import application.lushup.model.Customer;
import application.lushup.resources.CustomerResource;

public class LushupModule implements WebBeansModule
{

   public void configure(WebBeansBinder binder)
   {
      binder.install(new CoreModule());

      binder.install(new JSONModule());
      binder.install(new JSPModule());

      binder.install(new YahooModule());
      binder.install(new JQueryModule());

      binder.model(LushupConfiguration.class);
      binder.model(CustomerResource.class);
      binder.model(Customer.class);
   }

}
