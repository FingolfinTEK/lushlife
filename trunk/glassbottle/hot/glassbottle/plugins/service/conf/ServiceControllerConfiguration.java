package glassbottle.plugins.service.conf;

import glassbottle.plugins.module.dsl.ContextBinder;
import glassbottle.plugins.resources.binding.ResourceContextName;
import glassbottle.plugins.service.ServiceController;

import javax.enterprise.event.Observes;


public class ServiceControllerConfiguration
{

   @ResourceContextName
   private String name;

   public void init(@Observes ContextBinder configurator)
   {
      configurator.context(name).bindTo(ServiceController.class);
   }

}
