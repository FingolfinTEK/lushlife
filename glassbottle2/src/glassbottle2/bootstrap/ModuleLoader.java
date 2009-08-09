package glassbottle2.bootstrap;

import glassbottle2.BeanModule;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;
import org.jboss.webbeans.util.serviceProvider.ServiceLoader;

public class ModuleLoader
{

   static Log log = Logging.getLog(ModuleLoader.class);

   static public Iterable<BeanModule> loadModules(ClassLoader loader)
   {
      return ServiceLoader.load(BeanModule.class, loader);
   }
}
