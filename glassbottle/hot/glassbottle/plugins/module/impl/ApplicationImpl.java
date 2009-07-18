package glassbottle.plugins.module.impl;


import glassbottle.core.Injector;
import glassbottle.plugins.context.ActionContext;
import glassbottle.plugins.context.Namespace;
import glassbottle.plugins.controller.Controller;
import glassbottle.plugins.module.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;



public class ApplicationImpl implements Application
{
   Log log = Logging.getLog(ApplicationImpl.class);
   private Injector injector;
   private Class<? extends Controller> defaultController;
   private Map<String, Class<? extends Controller>> controllers;
   private Map<String, String> packageToContext = new HashMap<String, String>();

   public ApplicationImpl(Injector injector, Class<? extends Controller> defaultController, Map<String, Class<? extends Controller>> controllers)
   {
      this.injector = injector;
      this.defaultController = defaultController;
      this.controllers = controllers;
      for (Entry<String, Class<? extends Controller>> entry : controllers.entrySet())
      {
         packageToContext.put(entry.getValue().getPackage().getName(), entry.getKey());
      }
   }

   protected Controller defaultController()
   {
      Controller controller = injector.getInstanceByType(defaultController);
      Namespace namespace = injector.getInstanceByType(Namespace.class);
      namespace.setName(toContext(controller.getPackage().getName()));
      return controller;
   }

   @Override
   public Controller resolveController(ActionContext request)
   {
      String contextName = request.getContextName();
      return resolveController(contextName);
   }

   @Override
   public Controller resolveController(String contextName)
   {
      log.info("resolving context {0}", contextName);
      if (contextName == null)
      {
         log.info("resolve default context ");
         return defaultController();
      }
      Class<? extends Controller> controller = controllers.get(contextName);
      if (controller == null)
      {
         log.info("resolve default context ");
         return defaultController();
      }
      log.info("resolve {0}", controller);
      Controller controllerInstance = injector.getInstanceByType(controller);
      Namespace namespace = injector.getInstanceByType(Namespace.class);
      namespace.setName(contextName);
      return controllerInstance;
   }

   @Override
   public String toContext(String packageName)
   {
      return packageToContext.get(packageName);
   }
}
