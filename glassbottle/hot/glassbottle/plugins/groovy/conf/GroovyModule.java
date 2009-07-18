package glassbottle.plugins.groovy.conf;

import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;
import glassbottle.plugins.groovy.GSPTemplateEngine;
import glassbottle.plugins.groovy.GroovyShellManager;
import glassbottle.plugins.groovy.LFunction;
import glassbottle.plugins.groovy.impl.TemplateHandler;
import glassbottle.plugins.groovy.impl.TemplateManager;

public class GroovyModule implements WebBeansModule
{

   @Override
   public void configure(WebBeansBinder binder)
   {
      binder.clazz(GSPTemplateEngine.class);
      binder.clazz(GroovyShellManager.class);
      binder.clazz(LFunction.class);
      binder.clazz(TemplateManager.class);
      binder.clazz(TemplateHandler.class);
   }

}
