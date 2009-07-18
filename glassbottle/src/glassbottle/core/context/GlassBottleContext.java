package glassbottle.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;

import org.jboss.webbeans.context.api.BeanInstance;

public class GlassBottleContext
{
   static private ThreadLocal<ServletContext> servletContext = new ThreadLocal<ServletContext>();
   static private ThreadLocal<Map<Contextual<? extends Object>, BeanInstance<? extends Object>>> hiddenScope = new ThreadLocal<Map<Contextual<? extends Object>, BeanInstance<? extends Object>>>()
   {
      @Override
      protected Map<Contextual<? extends Object>, BeanInstance<? extends Object>> initialValue()
      {
         return new HashMap<Contextual<? extends Object>, BeanInstance<? extends Object>>();
      }
   };

   public static Map<Contextual<? extends Object>, BeanInstance<? extends Object>> getHiddenScope()
   {
      return hiddenScope.get();
   }

   @Produces
   public static ServletContext getServletContext()
   {
      ServletContext context = servletContext.get();
      if (context == null)
      {
         return null;
      }
      return context;
   }

   public static void setServletContext(ServletContext servletContext)
   {
      GlassBottleContext.servletContext.set(servletContext);
   }
}
