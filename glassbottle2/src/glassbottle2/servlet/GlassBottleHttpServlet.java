package glassbottle2.servlet;

import glassbottle2.GlassBottle;
import glassbottle2.GlassBottleContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlassBottleHttpServlet extends HttpServlet
{

   private static final long serialVersionUID = 1L;

   @Override
   protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
      GlassBottleContext.setResponse(arg1);
      GlassBottle.getInjector().getInstanceByType(GlassBottleHttpServletDispatcher.class).service(arg0, arg1);
   }

}
