package glassbottle2.extension.jsp;

import glassbottle2.binding.RequestInitialized;

import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import org.jboss.webbeans.el.WebBeansELResolver;

public class JSPInitializer
{
   static public boolean init = false;

   public void initELResolver(@Observes
   @RequestInitialized
   ServletContextEvent event)
   {
      if (init == false)
      {
         ServletContext context = event.getServletContext();
         JspFactory factory = JspFactory.getDefaultFactory();
         JspApplicationContext applicationContext = factory.getJspApplicationContext(context);
         applicationContext.addELResolver(new WebBeansELResolver());
         init = true;
      }

   }

}
