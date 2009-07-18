package glassbottle.plugins.context.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.context.ActionContext;
import glassbottle.plugins.context.ContextListener;
import glassbottle.plugins.context.EventScopedMap;
import glassbottle.plugins.context.HiddenContextManager;
import glassbottle.plugins.context.NamingResolverMap;
import glassbottle.plugins.context.impl.NamespaceImpl;
import glassbottle.plugins.context.impl.NamingResolverImpl;

public class ContextModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ContextListener.class);
      binder.clazz(ActionContext.class);
      binder.clazz(EventScopedMap.class);
      binder.clazz(NamingResolverImpl.class);
      binder.clazz(NamingResolverMap.class);
      binder.clazz(NamespaceImpl.class);
      binder.clazz(HiddenContextManager.class);
   }

}
