package glassbottle2;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.Named;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.webbeans.context.api.ContexutalInstance;

public class GlassBottleContext
{

   static private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
   static private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
   static private ThreadLocal<ServletContext> servletContext = new ThreadLocal<ServletContext>();
   static private ThreadLocal<Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>>> hiddenScope = new ThreadLocal<Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>>>()
   {
      @Override
      protected Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>> initialValue()
      {
         return new HashMap<Contextual<? extends Object>, ContexutalInstance<? extends Object>>();
      }
   };

   public static Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>> getHiddenScope()
   {
      return hiddenScope.get();
   }

   @Named("request")
   @Produces
   public static HttpServletRequest getRequest()
   {
      return request.get();
   }

   public static void setRequest(HttpServletRequest request)
   {
      GlassBottleContext.request.set(request);
   }

   @Named("response")
   @Produces
   public static HttpServletResponse getResponse()
   {
      return response.get();
   }

   public static void setResponse(HttpServletResponse response)
   {
      GlassBottleContext.response.set(response);
   }

   @Produces
   public static ServletContext getServletContext()
   {
      ServletContext context = servletContext.get();
      return context;
   }

   public static void setServletContext(ServletContext servletContext)
   {
      GlassBottleContext.servletContext.set(servletContext);
   }
}
