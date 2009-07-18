package glassbottle.core.conf;

import glassbottle.core.ClassLoaderProducer;
import glassbottle.core.Injector;
import glassbottle.core.StartupTimeProducer;
import glassbottle.core.context.LushContext;
import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;

public class CoreModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder module)
   {
      module.clazz(ClassLoaderProducer.class);
      module.clazz(StartupTimeProducer.class);
      module.clazz(LushContext.class);
      module.clazz(Injector.class);
   }

}
