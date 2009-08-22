package glassbottle2.context;

import glassbottle2.scope.Singleton;

import javax.enterprise.inject.Initializer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Singleton
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
      ServletContext context = request.getSession().getServletContext();
      this.servletPath = context.getInitParameter("resteasy.servlet.mapping.prefix");
   }

}