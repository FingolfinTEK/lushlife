package glassbottle.plugins.service;

import glassbottle.plugins.context.ActionContext;

import javax.enterprise.inject.Current;


public class ServiceContext
{
   @Current
   private ActionContext context;

   public String getClassName()
   {
      return context.getMethodName();
   }

   public String getContextName()
   {
      return context.getClassName();
   }

   public String getMethodName()
   {
      if (context.getParameters().length == 0)
      {
         return null;
      }
      return context.getParameters()[0];
   }

   public String getType()
   {
      if (context.getParameters().length <= 1)
      {
         return null;
      }
      return context.getParameters()[1];
   }

   public String[] getParameters()
   {
      if (context.getParameters().length <= 2)
      {
         return new String[0];
      }
      String[] tmp = new String[context.getParameters().length - 2];
      System.arraycopy(context.getParameters(), 2, tmp, 0, tmp.length);
      return tmp;

   }

   public String getServletPath()
   {
      return context.getServletPath();
   }

}
