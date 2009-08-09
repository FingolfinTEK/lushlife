package glassbottle2.extension.jquery;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.extension.resources.ResourceModule;

public class JQueryModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(JQueryResource.class);
      binder.install(new ResourceModule());
   }

}
