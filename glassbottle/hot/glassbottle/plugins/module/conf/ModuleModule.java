package glassbottle.plugins.module.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.module.ApplicationProducer;

public class ModuleModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ApplicationProducer.class);
   }

}
