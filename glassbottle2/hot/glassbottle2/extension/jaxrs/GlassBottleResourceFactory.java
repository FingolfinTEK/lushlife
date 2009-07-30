package glassbottle2.extension.jaxrs;

import glassbottle2.GlassBottle;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.ResourceFactory;

public class GlassBottleResourceFactory implements ResourceFactory
{

   private final Class<?> scannableClass;

   public GlassBottleResourceFactory(final Class<?> scannableClass)
   {
      this.scannableClass = scannableClass;
   }

   public Class<?> getScannableClass()
   {
      return scannableClass;
   }

   public void registered(final InjectorFactory factory)
   {
   }

   public Object createResource(final HttpRequest request, final HttpResponse response, final InjectorFactory factory)
   {
      return GlassBottle.getInjector().getInstance(scannableClass);
   }

   public void requestFinished(final HttpRequest request, final HttpResponse response, final Object resource)
   {
   }

   public void unregistered()
   {
   }
}
