package glassbottle.plugins.navigator.impl;

import glassbottle.plugins.navigator.Navigation;
import glassbottle.plugins.navigator.NavigationManager;
import glassbottle.plugins.navigator.Redirect;
import glassbottle.plugins.navigator.Render;
import glassbottle.plugins.view.ViewHandler;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.enterprise.inject.Current;
import javax.servlet.http.HttpServletResponse;


import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

public class NavigationManagerImpl implements NavigationManager
{
   Log log = Logging.getLog(NavigationManagerImpl.class);

   @Override
   public void navigate(Method method, Object returnValue)
   {
      log.info("navigate {0} {1}", method, returnValue);
   }

   @Current
   HttpServletResponse response;

   @Current
   ViewHandler handler;

   @Override
   public void navigate(Method method, Navigation navigation)
   {
      if (navigation instanceof Render)
      {
         handler.render(navigation.getPage());
         return;
      }
      if (navigation instanceof Redirect)
      {
         handler.redirect(navigation.getPage());
         return;
      }
      throw new RuntimeException("don't supported navigation " + navigation);
   }
}
