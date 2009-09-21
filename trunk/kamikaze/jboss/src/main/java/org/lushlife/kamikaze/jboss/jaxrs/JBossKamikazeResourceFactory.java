package org.lushlife.kamikaze.jboss.jaxrs;


import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.ResourceFactory;
import org.lushlife.kamikaze.Kamikaze;


public class JBossKamikazeResourceFactory implements ResourceFactory
{

   private final Class<?> scannableClass;

   public JBossKamikazeResourceFactory(final Class<?> scannableClass)
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
      return Kamikaze.getInjector().getInstance(scannableClass);
   }

   public void requestFinished(final HttpRequest request, final HttpResponse response, final Object resource)
   {
   }

   public void unregistered()
   {
   }
}
