package glassbottle.plugins.controller;

import glassbottle.plugins.controller.binding.ActionKey;

public class CommandManager
{
   @ActionKey
   String actionKey;

   public String toActionKey(String action)
   {
      return actionKey + action;
   }

   public boolean isActionKey(String name)
   {
      return name.startsWith(actionKey);
   }

   public String toAction(String name)
   {
      return name.substring(actionKey.length());
   }

}
