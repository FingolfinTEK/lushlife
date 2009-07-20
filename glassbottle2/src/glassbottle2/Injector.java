package glassbottle2;

import glassbottle2.scope.Singleton;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.enterprise.inject.Initializer;
import javax.enterprise.inject.spi.Bean;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.introspector.WBAnnotated;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;
import org.jboss.webbeans.resolution.ResolvableWBClass;

@Singleton
public class Injector implements Serializable
{
   private static final long serialVersionUID = -4385238949949794479L;
   static Log log = Logging.getLog(Injector.class);

   @Initializer
   public Injector(BeanManagerImpl manager)
   {
      super();
      this.manager = manager;
   }

   final private BeanManagerImpl manager;

   public <T> T getInstanceByType(Class<T> type, Annotation... bindings)
   {
      WBAnnotated<T, ?> element = ResolvableWBClass.of(type, bindings, manager);
      Bean<T> bean = manager.getBean(element, bindings);
      return (T) manager.getReference(bean, type, manager.createCreationalContext(bean));
   }

}
