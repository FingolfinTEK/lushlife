package glassbottle.extension.yahoo.conf;

import glassbottle.extension.yahoo.YahooController;
import glassbottle.plugins.module.dsl.ContextBinder;

import javax.enterprise.event.Observes;


public class YahooConfigurator
{
   public void init(@Observes ContextBinder config)
   {
      config.context("Yahoo").bindTo(YahooController.class);
   }
}
