package glassbottle2.el;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.el.impl.NamingResolverImpl;

public class ELModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(ELVariableEventScopedMap.class);
      binder.model(NamingResolverImpl.class);
      binder.model(NamingResolverMap.class);
   }

}
