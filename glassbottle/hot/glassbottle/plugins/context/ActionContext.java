package glassbottle.plugins.context;

import glassbottle.plugins.context.scope.EventScoped;

import javax.enterprise.inject.Initializer;
import javax.servlet.http.HttpServletRequest;


@EventScoped
public class ActionContext
{

   private String servletPath;
   private String contextName;
   private String className;
   private String methodName;

   public String getServletPath()
   {
      return servletPath;
   }

   private String[] parameters = new String[0];

   public String getContextName()
   {
      return contextName;
   }

   public String getClassName()
   {
      return className;
   }

   public String getMethodName()
   {
      return methodName;
   }

   public String[] getParameters()
   {
      return parameters;
   }

   @Initializer
   public ActionContext(HttpServletRequest request)
   {
      String str = request.getPathInfo();
      str = (str == null) ? "" : str.trim();
      String requestUri = request.getRequestURI();
      this.servletPath = requestUri.substring(1, requestUri.length() - str.length());
      parse(str);
   }

   protected void parse(String str)
   {
      if (str.length() == 0)
      {
         return;
      }
      String[] tmps = str.substring(1).split("/");
      this.contextName = tmps[0];
      if (tmps.length == 1)
      {
         return;
      }
      this.className = tmps[1];
      if (tmps.length == 2)
      {
         return;
      }
      this.methodName = tmps[2];
      if (tmps.length == 3)
      {
         return;
      }
      this.parameters = new String[tmps.length - 3];
      System.arraycopy(tmps, 3, parameters, 0, parameters.length);
   }

}