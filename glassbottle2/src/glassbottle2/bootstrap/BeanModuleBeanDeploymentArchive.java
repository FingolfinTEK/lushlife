package glassbottle2.bootstrap;

import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.webbeans.bootstrap.api.ServiceRegistry;
import org.jboss.webbeans.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.ejb.spi.EjbDescriptor;

public class BeanModuleBeanDeploymentArchive implements BeanDeploymentArchive
{

   static public class BeanBinderImpl implements BeanBinder
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
      public void model(Class<?> clazz)
      {
         classes.add(clazz);
      }

      @Override
      public void install(BeanModule module)
      {
         module.configure(this);
      }

      @Override
      public void beansXml(URL url)
      {
         xmls.add(url);
      }

   }

   final private BeanBinderImpl binder = new BeanBinderImpl();
   final private ServiceRegistry registry;

   public BeanModuleBeanDeploymentArchive(ServiceRegistry registry, Iterable<BeanModule> modules)
   {
      this.registry = registry;
      for (BeanModule module : modules)
      {
         module.configure(binder);
      }
   }

   @Override
   public Collection<Class<?>> getBeanClasses()
   {
      return binder.getClasses();
   }

   @Override
   public List<BeanDeploymentArchive> getBeanDeploymentArchives()
   {
      return new ArrayList<BeanDeploymentArchive>();
   }

   @Override
   public Collection<URL> getBeansXml()
   {
      return binder.getXmls();
   }

   @Override
   public Collection<EjbDescriptor<?>> getEjbs()
   {
      return new ArrayList<EjbDescriptor<?>>();
   }

   @Override
   public ServiceRegistry getServices()
   {
      return registry;
   }

}
