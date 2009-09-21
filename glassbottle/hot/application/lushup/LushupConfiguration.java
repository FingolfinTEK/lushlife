package application.lushup;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.lushlife.kamikaze.context.HiddenKey;

public class LushupConfiguration
{

   @Produces
   @Named("encoding")
   static final public String encoding = "utf8";

   @HiddenKey
   @Produces
   static final public String hiddenKey = "_lushup_";

}
