package glassbottle.plugins.service.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.service.ServiceController;
import glassbottle.plugins.service.impl.ServiceManagerImpl;
import glassbottle.plugins.service.impl.TypeFormatterRegistry;

public class ServiceModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ServiceControllerConfiguration.class);
      binder.clazz(ServiceController.class);
      binder.clazz(ServiceManagerImpl.class);
      binder.clazz(TypeFormatterRegistry.class);
   }

}
