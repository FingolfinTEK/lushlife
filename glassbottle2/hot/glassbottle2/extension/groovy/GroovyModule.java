package glassbottle2.extension.groovy;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.extension.groovy.impl.TemplateHandler;
import glassbottle2.extension.groovy.impl.TemplateManager;

public class GroovyModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.model(GroovyTemplateEngine.class);
      binder.model(GroovyShellManager.class);
      binder.model(LFunction.class);
      binder.model(TemplateManager.class);
      binder.model(TemplateHandler.class);
      // binder.clazz(GroovyListener.class);
   }

}
