package glassbottle.plugins.controller.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.controller.CommandManager;
import glassbottle.plugins.controller.ControllerServletDelegator;

public class ControllerModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ControllerServletDelegator.class);
      binder.clazz(CommandManager.class);
   }

}
