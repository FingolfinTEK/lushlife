package glassbottle.plugins.navigator;

import glassbottle.plugins.view.Page;

public class Redirect implements Navigation
{
   private Class<? extends Page> value;

   public Redirect(Class<? extends Page> value)
   {
      this.value = value;
   }

   public Class<? extends Page> getPage()
   {
      return value;
   }

}
