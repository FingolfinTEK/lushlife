package glassbottle.plugins.navigator.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.navigator.impl.NavigationManagerImpl;

public class NavigatorModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(NavigationManagerImpl.class);
   }

}
