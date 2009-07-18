package glassbottle.plugins.resources;

import glassbottle.plugins.context.ActionContext;

import javax.enterprise.inject.Current;


public class ResourceContext
{
   @Current
   private ActionContext context;

   public String getTypeName()
   {
      return context.getMethodName();
   }

   public String getContextName()
   {
      return context.getClassName();
   }

   public String getResourceName()
   {
      if (context.getParameters().length == 0)
      {
         return null;
      }
      return context.getParameters()[0];
   }

   public String getServletPath()
   {
      return context.getServletPath();
   }

}
