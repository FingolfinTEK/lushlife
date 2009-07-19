package glassbottle2.servlet;

import glassbottle2.GlassBottle;
import glassbottle2.jaxrs.JaxRSHttpServletDispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JaxRsHttpServlet extends HttpServlet
{

   @Override
   protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
      GlassBottle.getInjector().getInstanceByType(JaxRSHttpServletDispatcher.class).service(arg0, arg1);
   }

}
