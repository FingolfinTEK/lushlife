package glassbottle2.plugins.resources;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class ResourceModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ResourceCacheImpl.class);
   }

}
