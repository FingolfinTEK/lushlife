package glassbottle2.el;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.el.impl.NamingResolverImpl;

public class ELModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ELVariableEventScopedMap.class);
      binder.clazz(NamingResolverImpl.class);
      binder.clazz(NamingResolverMap.class);
   }

}
