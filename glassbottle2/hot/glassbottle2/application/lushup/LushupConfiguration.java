package glassbottle2.application.lushup;

import glassbottle2.binding.Encoding;
import glassbottle2.context.binding.HiddenKey;

import javax.enterprise.inject.Produces;

public class LushupConfiguration
{

   @Produces
   @Encoding
   static final public String encoding = "utf8";

   @HiddenKey
   @Produces
   static final public String hiddenKey = "_lushup_";

}
