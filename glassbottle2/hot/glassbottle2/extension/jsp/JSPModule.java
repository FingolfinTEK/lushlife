package glassbottle2.extension.jsp;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;

public class JSPModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(JSPPage.class);
   }

}
