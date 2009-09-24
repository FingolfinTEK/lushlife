package org.lushlife.kamikaze.mvc.context;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@javax.inject.Singleton
public class ServletPathInfo
{

   private String servletPath;

   public String getServletPath()
   {
      return servletPath;
   }

   @Inject
   public ServletPathInfo(@Named("request") HttpServletRequest request)
   {
      ServletContext context = request.getSession().getServletContext();
      this.servletPath = context.getInitParameter("resteasy.servlet.mapping.prefix");
   }

}