package glassbottle2.extension.json;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;

public class JSONModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(JSONWriter.class);
   }

}
