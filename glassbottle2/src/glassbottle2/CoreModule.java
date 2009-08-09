package glassbottle2;

import glassbottle2.util.UtilModule;
import glassbottle2.util.id.RequestIdGenerator;

public class CoreModule implements BeanModule
{

   @Override
   public void configure(BeanBinder module)
   {
      module.install(new UtilModule());
      module.model(GlassBottleContext.class);
      module.model(Injector.class);
      module.model(RequestIdGenerator.class);
   }

}
