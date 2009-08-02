package glassbottle2;

import glassbottle2.util.UtilModule;
import glassbottle2.util.id.RequestIdGenerator;

public class CoreModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder module)
   {
      module.install(new UtilModule());
      module.clazz(GlassBottleContext.class);
      module.clazz(Injector.class);
      module.clazz(RequestIdGenerator.class);
   }

}
