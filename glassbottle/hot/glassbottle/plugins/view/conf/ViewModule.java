package glassbottle.plugins.view.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.view.GSPPage;
import glassbottle.plugins.view.impl.ViewHandlerImpl;

public class ViewModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ViewHandlerImpl.class);
      binder.clazz(GSPPage.class);
   }

}
