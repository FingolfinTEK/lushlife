package glassbottle.plugins.view.impl;


import glassbottle.core.Injector;
import glassbottle.plugins.module.Application;
import glassbottle.plugins.view.Page;
import glassbottle.plugins.view.ViewHandler;

import java.io.IOException;

import javax.enterprise.inject.Current;
import javax.servlet.http.HttpServletResponse;



public class ViewHandlerImpl implements ViewHandler
{

   @Current
   private Injector injector;

   @Current
   private Application application;

   @Current
   private HttpServletResponse response;

   @Current
   glassbottle.plugins.context.ActionContext context;

   @Override
   public void render(Class<? extends Page> value)
   {
      Page page = injector.getInstanceByType(value);
      page.action();
      page.render();
   }

   @Override
   public void redirect(Class<? extends Page> value)
   {
      String context = application.toContext(value.getPackage().getName());
      String url = "/" + this.context.getServletPath() + "/" + context + "/" + value.getSimpleName();
      try
      {
         response.sendRedirect(url);
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }
}
