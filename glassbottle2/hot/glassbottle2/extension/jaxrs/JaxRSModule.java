package glassbottle2.extension.jaxrs;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;

public class JaxRSModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(JaxRSInitializer.class);
      binder.model(JaxRsServletDispatcher.class);
   }

}
