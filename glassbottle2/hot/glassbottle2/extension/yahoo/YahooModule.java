package glassbottle2.extension.yahoo;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.extension.resources.ResourceModule;

public class YahooModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(YahooResource.class);
      binder.install(new ResourceModule());
   }

}
