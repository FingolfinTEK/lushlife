package glassbottle.plugins.service.impl;


import glassbottle.core.Injector;
import glassbottle.plugins.controller.Controller;
import glassbottle.plugins.controller.ControllerManager;
import glassbottle.plugins.module.Application;
import glassbottle.plugins.service.ServiceManager;
import glassbottle.util.beans.BeansUtil;
import glassbottle.util.io.WriteTo;

import java.lang.reflect.Method;

import javax.enterprise.inject.Current;



public class ServiceManagerImpl implements ServiceManager
{
   @Current
   private Application application;
   @Current
   private ClassLoader loader;

   @Current
   private Injector injector;

   @Current
   ControllerManager controllerManager;

   @Current
   private TypeFormatterRegistry formatter;

   @Override
   public WriteTo invokeAndCreateResponseFormatter(String context, String clazz, String method, String type)
   {
      Controller oldController = controllerManager.getController();
      try
      {
         Controller controller = application.resolveController(context);
         controllerManager.setController(controller);
         Package targetPackage = controller.getPackage();
         String fullClassName = targetPackage.getName() + ".service." + BeansUtil.toClassName(clazz);
         Class<?> clazzClass = loader.loadClass(fullClassName);
         Method methodMethod = clazzClass.getMethod(method);
         Object instance = injector.getInstanceByType(clazzClass);
         Object output = methodMethod.invoke(instance);
         return formatter.get(type).write(output);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         controllerManager.setController(oldController);
      }
   }
}
