package glassbottle.plugins.module;


import glassbottle.core.Injector;
import glassbottle.core.scope.Singleton;
import glassbottle.plugins.controller.Controller;
import glassbottle.plugins.module.dsl.ApplicationBinder;
import glassbottle.plugins.module.dsl.ControllerBinding;
import glassbottle.plugins.module.impl.ApplicationImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.Current;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;


import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;


public class ApplicationProducer
{
   Log log = Logging.getLog(ApplicationProducer.class);

   @Current
   private BeanManager manager;
   @Current
   private Injector injector;

   @Singleton
   @Produces
   public Application produce()
   {
      final Class<? extends Controller>[] defaultController = new Class[1];
      final Map<String, Class<? extends Controller>> bindings = new ConcurrentHashMap<String, Class<? extends Controller>>();
      ApplicationBinder binder = new ApplicationBinder()
      {
         @Override
         public ControllerBinding context(final String contextName)
         {
            return new ControllerBinding()
            {
               @Override
               public void bindTo(final Class<? extends Controller> controller)
               {
                  bindings.put(contextName, controller);
               }
            };
         }

         @Override
         public ControllerBinding defaultController(final String name)
         {
            return new ControllerBinding()
            {
               @Override
               public void bindTo(Class<? extends Controller> controller)
               {
                  defaultController[0] = controller;
                  bindings.put(name, controller);
               }
            };
         }
      };
      manager.fireEvent(binder);
      log.info("default controller[{0}] binding controller[{1}]", defaultController[0], bindings);
      return new ApplicationImpl(injector, defaultController[0], bindings);
   }
}
