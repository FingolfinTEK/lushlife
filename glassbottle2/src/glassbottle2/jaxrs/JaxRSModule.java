package glassbottle2.jaxrs;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;

public class JaxRSModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(ResteasyBootstrap.class);
      binder.clazz(JaxRSInitializer.class);
   }

}
