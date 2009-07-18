package glassbottle.core.webbeans;


import glassbottle.core.dsl.WebBeansBinder;
import glassbottle.core.dsl.WebBeansModule;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;


public class GlassBottleBeanDeploymentArchive implements BeanDeploymentArchive
{

   private WebBeansBinderImpl binder = new WebBeansBinderImpl();

   static public class WebBeansBinderImpl implements WebBeansBinder
   {
      private Set<Class<?>> classes = new HashSet<Class<?>>();
      private Set<URL> xmls = new HashSet<URL>();

      public Set<Class<?>> getClasses()
      {
         return classes;
      }

      public Set<URL> getXmls()
      {
         return xmls;
      }

      @Override
      public void clazz(Class<?> clazz)
      {
         classes.add(clazz);
      }

      @Override
      public void install(WebBeansModule module)
      {
         module.configure(this);
      }

      @Override
      public void xml(URL url)
      {
         xmls.add(url);
      }

   }

   public GlassBottleBeanDeploymentArchive(WebBeansModule... modules)
   {
      this(Arrays.asList(modules));
   }

   public GlassBottleBeanDeploymentArchive(Iterable<WebBeansModule> modules)
   {
      for (WebBeansModule module : modules)
      {
         module.configure(binder);
      }
   }

   public Iterable<Class<?>> discoverWebBeanClasses()
   {
      return binder.getClasses();
   }

   public Iterable<URL> discoverWebBeansXml()
   {
      return binder.getXmls();
   }

   @Override
   public Iterable<Class<?>> getBeanClasses()
   {
      return binder.getClasses();
   }

   @Override
   public List<BeanDeploymentArchive> getBeanDeploymentArchives()
   {
      return new ArrayList<BeanDeploymentArchive>();
   }

   @Override
   public Iterable<URL> getBeansXml()
   {
      return binder.getXmls();
   }

}
