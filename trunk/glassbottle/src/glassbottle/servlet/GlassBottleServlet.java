package glassbottle.servlet;


import glassbottle.GlassBottle;
import glassbottle.spi.HttpServletDelegator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class GlassBottleServlet extends HttpServlet
{
   private static final long serialVersionUID = -8708166997089268875L;

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      GlassBottle.getInjector().getInstanceByType(HttpServletDelegator.class)//
            .delegate(request, response);
   }

}
