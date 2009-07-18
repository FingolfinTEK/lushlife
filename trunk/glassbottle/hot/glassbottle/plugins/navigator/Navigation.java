package glassbottle.plugins.navigator;

import glassbottle.plugins.view.Page;

public interface Navigation
{
   public Class<? extends Page> getPage();
}
