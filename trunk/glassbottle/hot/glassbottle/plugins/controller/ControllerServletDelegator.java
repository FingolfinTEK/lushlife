package glassbottle.plugins.controller;


import glassbottle.plugins.context.ActionContext;
import glassbottle.plugins.context.scope.EventScoped;
import glassbottle.plugins.module.Application;
import glassbottle.spi.HttpServletDelegator;

import javax.enterprise.inject.Current;
import javax.enterprise.inject.Named;
import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@EventScoped
public class ControllerServletDelegator implements HttpServletDelegator
{

   @Named("request")
   @Produces
   private HttpServletRequest request;

   @Named("response")
   @Produces
   private HttpServletResponse response;

   @Current
   private ControllerManager manager;

   @Current
   Application application;

   @Override
   public void delegate(HttpServletRequest request, HttpServletResponse response)
   {
      this.request = request;
      this.response = response;
      Controller controller = application.resolveController(new ActionContext(request));
      manager.setController(controller);
      controller.controll();
   }

}
