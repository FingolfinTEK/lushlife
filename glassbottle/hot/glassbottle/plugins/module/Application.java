package glassbottle.plugins.module;

import glassbottle.plugins.context.ActionContext;
import glassbottle.plugins.controller.Controller;

public interface Application
{
   Controller resolveController(ActionContext request);

   Controller resolveController(String contextName);

   String toContext(String packageName);
}
