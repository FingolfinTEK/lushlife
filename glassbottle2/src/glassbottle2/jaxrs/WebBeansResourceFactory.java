package glassbottle2.jaxrs;

import glassbottle2.GlassBottle;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.ResourceFactory;

public class WebBeansResourceFactory implements ResourceFactory
{
   private Class<?> scannableClass;

   public WebBeansResourceFactory(Class<?> scannableClass)
   {
      this.scannableClass = scannableClass;
   }

   public void registered(InjectorFactory factory)
   {
   }

   public Object createResource(HttpRequest request, HttpResponse response, InjectorFactory factory)
   {
      return GlassBottle.getInjector().getInstanceByType(scannableClass);
   }

   public void unregistered()
   {
   }

   public Class<?> getScannableClass()
   {
      return scannableClass;
   }

   public void requestFinished(HttpRequest request, HttpResponse response, Object resource)
   {
   }
}
