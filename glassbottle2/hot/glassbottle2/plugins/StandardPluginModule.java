package glassbottle2.plugins;

import glassbottle2.CoreModule;
import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.extension.groovy.GroovyModule;
import glassbottle2.jaxrs.JaxRSModule;
import glassbottle2.plugins.context.ContextModule;
import glassbottle2.plugins.resources.ResourceModule;

public class StandardPluginModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.install(new CoreModule());
      binder.install(new JaxRSModule());
      // binder.install(new ControllerModule());
      binder.install(new ContextModule());
      // binder.install(new ModuleModule());
      // binder.install(new NavigatorModule());
      binder.install(new ResourceModule());
      // binder.install(new ServiceModule());
   }

}
