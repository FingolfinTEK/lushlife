package glassbottle.plugins.resources.conf;

import glassbottle.plugins.module.dsl.ContextBinder;
import glassbottle.plugins.resources.ResourceController;
import glassbottle.plugins.resources.binding.ResourceContextName;

import javax.enterprise.event.Observes;


public class ResourceControllerConfiguration
{

   @ResourceContextName
   private String name;

   public void init(@Observes ContextBinder configurator)
   {
      configurator.context(name).bindTo(ResourceController.class);
   }

}
