package glassbottle2.util;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.util.date.StartupTimeProducer;
import glassbottle2.util.loader.ClassLoaderProducer;

public class UtilModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(ClassLoaderProducer.class);
      binder.model(StartupTimeProducer.class);
   }

}
