package glassbottle2;

import glassbottle2.util.UtilModule;

public class CoreModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder module)
   {
      module.install(new UtilModule());
      module.clazz(GlassBottleContext.class);
      module.clazz(Injector.class);
   }

}
