package glassbottle2.context;

import glassbottle2.scope.EventScoped;

import javax.enterprise.inject.Initializer;
import javax.servlet.http.HttpServletRequest;

@EventScoped
public class ServletPathInfo
{

   private String servletPath;

   public String getServletPath()
   {
      return servletPath;
   }

   @Initializer
   public ServletPathInfo(HttpServletRequest request)
   {
      String str = request.getPathInfo();
      str = (str == null) ? "" : str.trim();
      String requestUri = request.getRequestURI();
      this.servletPath = requestUri.substring(1, requestUri.length() - str.length());
   }

}