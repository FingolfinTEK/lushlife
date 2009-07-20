package glassbottle2.extension.json;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class JSONModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(JSONWriter.class);
   }

}
