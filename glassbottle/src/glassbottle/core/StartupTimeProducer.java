package glassbottle.core;


import glassbottle.core.binding.StartupTime;
import glassbottle.servlet.GlassBottleListener;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;



@ApplicationScoped
public class StartupTimeProducer implements Serializable
{
   private static final long serialVersionUID = 998095197676470759L;

   @Current
   ServletContext context;

   @Produces
   @StartupTime
   public Long startupTime()
   {
      return (Long) context.getAttribute(GlassBottleListener.LAST_UPDATE_TIME);
   }
}
