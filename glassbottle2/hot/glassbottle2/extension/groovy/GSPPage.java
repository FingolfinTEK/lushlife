package glassbottle2.extension.groovy;

import glassbottle2.plugins.context.NamingResolverMap;
import glassbottle2.plugins.view.Page;
import glassbottle2.util.beans.BeansUtil;
import glassbottle2.util.loader.ClassLoaderUtil;
import groovy.lang.Writable;
import groovy.text.Template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.enterprise.inject.Current;
import javax.ws.rs.WebApplicationException;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

public abstract class GSPPage implements Page
{
   Log log = Logging.getLog(GSPPage.class);

   @Current
   private GSPTemplateEngine engine;

   @Current
   private NamingResolverMap map;

   @Override
   public void write(OutputStream arg0) throws IOException, WebApplicationException
   {
      String classpath = resolveClassPath();
      Template template = engine.create(classpath);
      Writable writable = template.make(map);
      try
      {
         PrintWriter writer = new PrintWriter(arg0);
         writable.writeTo(writer);
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   protected String resolveClassPath()
   {
      Class<?> clazz = this.getClass();
      String packageName = clazz.getPackage().getName();
      String resourceName = BeansUtil.toPropertyName(clazz.getSimpleName()) + ".gsp";
      return ClassLoaderUtil.toClassPath(packageName, resourceName);
   }

}
