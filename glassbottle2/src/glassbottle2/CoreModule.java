package glassbottle2;

public class CoreModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder module)
   {
      module.clazz(ClassLoaderProducer.class);
      module.clazz(StartupTimeProducer.class);
      module.clazz(GlassBottleContext.class);
      module.clazz(Injector.class);
   }

}
