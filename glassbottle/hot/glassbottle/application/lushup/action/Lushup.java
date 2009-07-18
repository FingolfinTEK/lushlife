package glassbottle.application.lushup.action;

import glassbottle.application.lushup.view.Test;
import glassbottle.plugins.navigator.Navigation;
import glassbottle.plugins.navigator.Render;

public class Lushup
{

   public Navigation invoke()
   {
      return new Render(Test.class);
   }

}
