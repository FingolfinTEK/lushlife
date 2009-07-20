package glassbottle2.servlet;

import glassbottle2.GlassBottle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlassBottleHttpServlet extends HttpServlet
{

   @Override
   protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
      ServletManager manager = GlassBottle.getInjector().getInstanceByType(ServletManager.class);
      manager.setRequest(arg0);
      manager.setResponse(arg1);
      GlassBottle.getInjector().getInstanceByType(GlassBottleHttpServletDispatcher.class).service(arg0, arg1);
   }

}
