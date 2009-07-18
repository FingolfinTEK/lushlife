package glassbottle.plugins.controller;


import glassbottle.core.Injector;
import glassbottle.plugins.context.ActionContext;
import glassbottle.plugins.context.scope.EventScoped;
import glassbottle.plugins.navigator.Navigation;
import glassbottle.plugins.navigator.NavigationManager;
import glassbottle.plugins.navigator.Render;
import glassbottle.plugins.view.Page;
import glassbottle.util.beans.BeansUtil;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.enterprise.inject.Current;
import javax.servlet.http.HttpServletRequest;



@EventScoped
public abstract class ActionController implements Controller
{
   private Package controllerPackage;

   String contextName;

   public ActionController()
   {
      this.controllerPackage = this.getClass().getPackage();
   }

   public final Package getPackage()
   {
      return controllerPackage;
   }

   @Current
   private ActionContext context;

   @Current
   private Injector injector;

   @Current
   private ClassLoader loader;

   @Current
   private NavigationManager manager;

   @Current
   HttpServletRequest requset;

   @Current
   CommandManager commandManager;

   @Override
   public void controll()
   {
      try
      {
         ClassAndMethod classAndMethod = loadActionClassFromCommandButton();
         Object returnValue = null;
         if (classAndMethod != null)
         {
            Object instance = injector.getInstanceByType(classAndMethod.getClazz());
            returnValue = classAndMethod.getMethod().invoke(instance);
         }
         if (returnValue != null)
         {
            if (returnValue instanceof Navigation)
            {
               manager.navigate(classAndMethod.getMethod(), (Navigation) returnValue);
            }
            else
            {
               manager.navigate(classAndMethod.getMethod(), returnValue);
            }
         }
         else
         {
            Class<? extends Page> pageClass = loadPageClass();
            manager.navigate(null, new Render(pageClass));
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }

   }

   private ClassAndMethod loadActionClassFromCommandButton()
   {
      Enumeration<String> paramNames = requset.getParameterNames();
      while (paramNames.hasMoreElements())
      {
         String name = paramNames.nextElement();
         if (commandManager.isActionKey(name))
         {
            String action = commandManager.toAction(name);
            String[] temp = action.split("\\.");
            String className = BeansUtil.toClassName(temp[0]);
            String methodName = (temp.length > 1) ? temp[1] : "invoke";
            String fullClassName = controllerPackage.getName() + ".action." + className;
            Class<?> clazz;
            Method method;
            try
            {
               clazz = loader.loadClass(fullClassName);
               method = clazz.getMethod(methodName);
               return new ClassAndMethod(clazz, method);
            }
            catch (Exception e)
            {
               throw new RuntimeException(e);
            }
         }
      }

      return null;
   }

   private Class<? extends Page> loadPageClass() throws ClassNotFoundException
   {
      String className = context.getClassName();
      if (className == null)
      {
         className = "Index";
      }
      String fullClassName = controllerPackage.getName() + ".view." + className;

      return (Class<? extends Page>) loader.loadClass(fullClassName);
   }

}
