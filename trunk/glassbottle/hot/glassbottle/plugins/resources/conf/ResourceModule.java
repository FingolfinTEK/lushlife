package glassbottle.plugins.resources.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.resources.ResourceController;
import glassbottle.plugins.resources.impl.ResourceManagerImpl;

public class ResourceModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ResourceManagerImpl.class);
      binder.clazz(ResourceControllerConfiguration.class);
      binder.clazz(ResourceController.class);
   }

}
