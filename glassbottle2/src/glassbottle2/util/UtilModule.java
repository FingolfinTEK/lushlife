package glassbottle2.util;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.util.date.StartupTimeProducer;
import glassbottle2.util.loader.ClassLoaderProducer;

public class UtilModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ClassLoaderProducer.class);
      binder.clazz(StartupTimeProducer.class);

   }

}
