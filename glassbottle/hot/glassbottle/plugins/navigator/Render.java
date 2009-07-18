package glassbottle.plugins.navigator;

import glassbottle.plugins.view.Page;

public class Render implements Navigation
{
   private Class<? extends Page> value;

   public Class<? extends Page> getPage()
   {
      return value;
   }

   public Render(Class<? extends Page> value)
   {
      this.value = value;
   }

}
