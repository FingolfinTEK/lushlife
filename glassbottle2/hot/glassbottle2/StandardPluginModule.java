package glassbottle2;

import glassbottle2.CoreModule;
import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.context.ContextModule;

public class StandardPluginModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.install(new CoreModule());
      binder.install(new ContextModule());
   }

}
