package glassbottle.plugins.module.dsl;

import glassbottle.plugins.controller.Controller;

public interface ControllerBinding
{
   public void bindTo(Class<? extends Controller> controller);
}
