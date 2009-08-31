package glassbottle2.util.date;

import glassbottle2.binding.StartupTime;
import glassbottle2.servlet.GlassBottleListener;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;

public class StartupTimeProducer implements Serializable
{
   private static final long serialVersionUID = 998095197676470759L;

   @Inject
   transient ServletContext context;

   @Produces
   @StartupTime
   public Long startupTime()
   {
      return (Long) context.getAttribute(GlassBottleListener.LAST_UPDATE_TIME);
   }
}
