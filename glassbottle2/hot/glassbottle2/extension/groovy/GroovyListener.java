//package glassbottle2.extension.groovy;
//
//import javax.enterprise.event.Observes;
//import javax.servlet.ServletContext;
//
//import glassbottle2.GlassBottleContext;
//import glassbottle2.binding.RequestDestroyed;
//import glassbottle2.binding.RequestInitialized;
//import glassbottle2.servlet.HttpServletEvent;
//
//import org.codehaus.groovy.grails.web.servlet.FlashScope;
//import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
//import org.codehaus.groovy.grails.web.servlet.GrailsFlashScope;
//import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//
//public class GroovyListener
//{
//
//   public void init(@Observes
//   @RequestInitialized
//   HttpServletEvent event)
//   {
//      ServletContext servletContext = GlassBottleContext.getServletContext();
//      GrailsWebRequest attr = new GrailsWebRequest(event.getRequest(), event.getResponse(), servletContext)
//      {
//         FlashScope flashScope = new GrailsFlashScope();
//
//         @Override
//         public FlashScope getFlashScope()
//         {
//            return flashScope;
//         }
//      };
//      event.getRequest().setAttribute(GrailsApplicationAttributes.WEB_REQUEST, attr);
//      RequestContextHolder.setRequestAttributes(attr);
//   }
//
//   public void destroy(@Observes
//   @RequestDestroyed
//   HttpServletEvent event)
//   {
//      RequestContextHolder.resetRequestAttributes();
//   }
//}
