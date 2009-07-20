package glassbottle2.extension.groovy;

import glassbottle2.WebBeansBinder;
import glassbottle2.WebBeansModule;
import glassbottle2.extension.groovy.impl.TemplateHandler;
import glassbottle2.extension.groovy.impl.TemplateManager;

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
