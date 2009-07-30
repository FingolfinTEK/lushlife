package glassbottle2.servlet;

import glassbottle2.GlassBottle;
import glassbottle2.GlassBottleContext;
import glassbottle2.binding.RequestDestroyedLiteral;
import glassbottle2.binding.RequestInitializedLiteral;

import java.io.IOException;

import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.webbeans.CurrentManager;

public class GlassBottleHttpServlet extends HttpServlet
{

   private static final long serialVersionUID = 1L;

   @Override
   protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
      GlassBottleContext.setResponse(arg1);
      BeanManager manager = CurrentManager.rootManager();
      HttpServletEvent event = new HttpServletEvent(arg0, arg1);
      manager.fireEvent(event, new RequestInitializedLiteral());
      try
      {
         GlassBottle.getInjector().getInstance(GlassBottleHttpServletDispatcher.class).service(arg0, arg1);
      }
      finally
      {
         manager.fireEvent(event, new RequestDestroyedLiteral());
      }
   }
}
