package glassbottle2.extension.jsp;

import glassbottle2.binding.RequestInitialized;
import glassbottle2.el.NamingResolverMap;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.Instance;
import javax.servlet.ServletRequestEvent;

public class JSPInitializer
{
   static public boolean init = false;

   @Current
   Instance<NamingResolverMap> map;

   public void initELResolver(@Observes
   @RequestInitialized
   ServletRequestEvent event)
   {
      // event.getServletRequest().setAttribute("w", map);
      // if (init == false)
      // {
      // ServletContext context = event.getServletContext();
      // JspFactory factory = JspFactory.getDefaultFactory();
      // System.out.println(factory.getClass());
      // for (Method method : factory.getClass().getMethods())
      // {
      // System.out.println(method);
      // }
      // JspApplicationContext applicationContext =
      // factory.getJspApplicationContext(context);
      // applicationContext.addELResolver(new WebBeansELResolver());
      // init = true;
      // }

   }

}
