package glassbottle2.extension.jspquery;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;

public class JSPQueryModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(JSPQueryManager.class);
   }

}
