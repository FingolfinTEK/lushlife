package glassbottle.plugins.resources;

import glassbottle.plugins.context.ActionContext;
import glassbottle.plugins.context.scope.EventScoped;
import glassbottle.plugins.controller.Controller;

import java.io.IOException;

import javax.enterprise.inject.Current;
import javax.servlet.http.HttpServletResponse;


@EventScoped
public class ResourceController implements Controller
{

   Package pack = this.getClass().getPackage();

   @Current
   ResourceContext context;

   @Current
   ResourceManager manager;

   @Current
   HttpServletResponse response;

   @Override
   public void controll()
   {
      try
      {
         manager.write(context.getContextName(), context.getTypeName(), context.getResourceName()).to(response.getOutputStream());
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   @Override
   public Package getPackage()
   {
      return pack;
   }

}
