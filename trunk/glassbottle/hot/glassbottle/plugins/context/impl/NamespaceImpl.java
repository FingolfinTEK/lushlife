package glassbottle.plugins.context.impl;

import glassbottle.plugins.context.Namespace;
import glassbottle.plugins.context.scope.EventScoped;

@EventScoped
public class NamespaceImpl implements Namespace
{
   String name;

   @Override
   public String getName()
   {
      return name;
   }

   @Override
   public void setName(String namespace)
   {
      this.name = namespace;
   }
}
