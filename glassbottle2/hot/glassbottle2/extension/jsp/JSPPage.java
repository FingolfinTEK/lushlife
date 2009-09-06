package glassbottle2.extension.jsp;

import glassbottle2.GlassBottle;
import glassbottle2.view.Page;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

public class JSPPage implements Page
{
   static public Page to(String path)
   {
      return GlassBottle.getInjector().getInstance(JSPPage.class).path(path);
   }

   private String path;

   @Inject
   @Named("request")
   private HttpServletRequest reqeust;

   @Inject
   @Named("response")
   private HttpServletResponse response;

   @Inject
   private ServletContext context;

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
         context.getRequestDispatcher(path).include(reqeust, response);
      }
      catch (ServletException e)
      {
         throw new WebApplicationException(e);
      }
   }

}
