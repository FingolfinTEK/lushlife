package glassbottle2.extension.jaxrs;

import glassbottle2.bootstrap.GlassBottleDeployment;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;

public class JaxRSInitializer
{
   private ResteasyBootstrap resteasyBootstrap = new ResteasyBootstrap();

   @Current
   private ServletContext context;

   public void contextInitialized(@Observes
   GlassBottleDeployment deployment)
   {
      ServletContextEvent event = new ServletContextEvent(context);
      resteasyBootstrap.contextInitialized(event);
      final ServletContext context = event.getServletContext();
      final Registry registry = (Registry) context.getAttribute(Registry.class.getName());
      final ResteasyProviderFactory providerFactory = (ResteasyProviderFactory) context.getAttribute(ResteasyProviderFactory.class.getName());
      final ModuleProcessor processor = new ModuleProcessor(registry, providerFactory);
      for (BeanDeploymentArchive archive : deployment.getBeanDeploymentArchives())
      {
         processor.process(archive.getBeanClasses());
      }
   }

   public void destroy(BeforeShutdown shutdown)
   {
      resteasyBootstrap.contextDestroyed(new ServletContextEvent(context));
   }

}
