package glassbottle.plugins;

import glassbottle.core.conf.CoreModule;
import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.context.conf.ContextModule;
import glassbottle.plugins.controller.conf.ControllerModule;
import glassbottle.plugins.groovy.conf.GroovyModule;
import glassbottle.plugins.module.conf.ModuleModule;
import glassbottle.plugins.navigator.conf.NavigatorModule;
import glassbottle.plugins.resources.conf.ResourceModule;
import glassbottle.plugins.service.conf.ServiceModule;
import glassbottle.plugins.view.conf.ViewModule;

public class StandardPluginModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.install(new CoreModule());
      binder.install(new ControllerModule());
      binder.install(new ContextModule());
      binder.install(new ModuleModule());
      binder.install(new NavigatorModule());
      binder.install(new ViewModule());
      binder.install(new GroovyModule());
      binder.install(new ResourceModule());
      binder.install(new ServiceModule());
   }

}
