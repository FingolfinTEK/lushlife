package glassbottle2.extension.jsp;

import glassbottle2.view.Page;

import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.inject.Current;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;
import javax.ws.rs.WebApplicationException;

import org.jboss.webbeans.el.WebBeansELResolver;

public class JSPPage implements Page
{
   private String path;

   @Current
   HttpServletRequest reqeust;

   @Current
   HttpServletResponse response;

   @Current
   ServletContext context;

   public JSPPage path(String path)
   {
      this.path = path;
      return this;
   }

   @Override
   public void write(OutputStream arg0) throws IOException, WebApplicationException
   {
      try
      {
         response.reset();
         context.getRequestDispatcher(path).forward(reqeust, response);
      }
      catch (ServletException e)
      {
         throw new WebApplicationException(e);
      }
   }

}
