package glassbottle2.jaxrs;

import java.io.IOException;
import java.util.Enumeration;

import javax.enterprise.inject.Initializer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class JaxRSHttpServletDispatcher
{
   HttpServletDispatcher dispatcher = new HttpServletDispatcher();

   @Initializer
   public JaxRSHttpServletDispatcher(final ServletContext context) throws ServletException
   {
      dispatcher.init(new ServletConfig()
      {

         @Override
         public String getInitParameter(String arg0)
         {
            return context.getInitParameter(arg0);
         }

         @Override
         public Enumeration<String> getInitParameterNames()
         {
            return context.getInitParameterNames();
         }

         @Override
         public ServletContext getServletContext()
         {
            return context;
         }

         @Override
         public String getServletName()
         {
            return JaxRSHttpServletDispatcher.class.getName();
         }

      });

   }

   public void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
      dispatcher.service(arg0, arg1);
   }

}
