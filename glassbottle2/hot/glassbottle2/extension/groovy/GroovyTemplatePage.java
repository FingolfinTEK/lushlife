package glassbottle2.extension.groovy;

import glassbottle2.binding.Encoding;
import glassbottle2.el.NamingResolverMap;
import glassbottle2.util.beans.BeansUtil;
import glassbottle2.util.loader.ClassLoaderUtil;
import glassbottle2.view.Page;
import groovy.lang.Writable;
import groovy.text.Template;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.enterprise.inject.Current;
import javax.ws.rs.WebApplicationException;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

public abstract class GroovyTemplatePage implements Page
{
   Log log = Logging.getLog(GroovyTemplatePage.class);

   @Current
   private GroovyTemplateEngine engine;

   @Current
   private NamingResolverMap map;

   @Encoding
   String encoding;

   @Override
   public void write(OutputStream arg0) throws IOException, WebApplicationException
   {
      String classpath = resolveClassPath();
      Template template = engine.create(classpath);
      Writable writable = template.make(map);
      try
      {
         // PrintWriter writer = new PrintWriter(arg0);
         Writer writer = new BufferedWriter(new OutputStreamWriter(arg0, encoding));

         writable.writeTo(writer);
         writer.flush();
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
