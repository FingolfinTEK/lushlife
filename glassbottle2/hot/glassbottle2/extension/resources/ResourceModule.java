package glassbottle2.extension.resources;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.extension.jaxrs.JaxRSModule;

public class ResourceModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.install(new JaxRSModule());
      binder.clazz(ResourceCacheImpl.class);
   }

}
