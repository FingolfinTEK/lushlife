package glassbottle2.extension.resources;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.extension.jaxrs.JaxRSModule;
import glassbottle2.extension.jspquery.JSPQueryModule;

public class ResourceModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.install(new JaxRSModule());
      binder.install(new JSPQueryModule());
      binder.model(ResourceCacheImpl.class);
      binder.model(ResourceManager.class);
   }

}
