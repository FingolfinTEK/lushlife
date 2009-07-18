package glassbottle.application.lushup.conf;


import glassbottle.application.lushup.LushupController;
import glassbottle.core.binding.Encoding;
import glassbottle.plugins.context.binding.HiddenKey;
import glassbottle.plugins.controller.binding.ActionKey;
import glassbottle.plugins.module.dsl.ApplicationBinder;
import glassbottle.plugins.resources.binding.ResourceContextName;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;



public class LushupApplicationConfiguration
{

   public void configuration(@Observes ApplicationBinder config)
   {
      config.defaultController("Lushup").bindTo(LushupController.class);
   }

   @Produces
   @Encoding
   String encoding = "utf8";

   @Produces
   @ResourceContextName
   String resourceContext = "Resources";

   @HiddenKey
   @Produces
   String hiddenKey = "_lushup_";

   @ActionKey
   @Produces
   String actionKey = "_action_";

}
