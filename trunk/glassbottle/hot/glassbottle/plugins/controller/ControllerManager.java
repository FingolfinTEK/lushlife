package glassbottle.plugins.controller;

import glassbottle.plugins.context.scope.EventScoped;
import glassbottle.plugins.controller.binding.ActiveController;

import javax.enterprise.inject.Produces;


@EventScoped
public class ControllerManager
{
   private Controller controller;

   @ActiveController
   @Produces
   public Controller getController()
   {
      return controller;
   }

   public void setController(Controller controller)
   {
      this.controller = controller;
   }

}
