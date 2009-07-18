package glassbottle.plugins.service;

import glassbottle.plugins.controller.Controller;

import java.io.IOException;

import javax.enterprise.inject.Current;
import javax.servlet.http.HttpServletResponse;


public class ServiceController implements Controller
{
   Package pack = this.getClass().getPackage();

   @Current
   private ServiceContext context;

   @Current
   private ServiceManager manager;

   @Current
   private HttpServletResponse response;

   @Override
   public void controll()
   {
      try
      {
         manager.invokeAndCreateResponseFormatter(context.getContextName(), context.getClassName(), context.getMethodName(), context.getType())//
               .to(response.getOutputStream());
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
