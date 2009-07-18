package glassbottle.plugins.view;

import glassbottle.core.binding.Encoding;
import glassbottle.plugins.context.NamingResolverMap;
import glassbottle.plugins.groovy.GSPTemplateEngine;
import glassbottle.util.beans.BeansUtil;
import glassbottle.util.loader.ClassLoaderUtil;
import groovy.lang.Writable;
import groovy.text.Template;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.inject.Current;
import javax.servlet.http.HttpServletResponse;


import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;


public abstract class GSPPage implements Page
{
   Log log = Logging.getLog(GSPPage.class);

   @Current
   private GSPTemplateEngine engine;

   @Current
   private NamingResolverMap map;

   @Current
   private HttpServletResponse response;

   @Encoding
   private String encoding;

   protected void writeHeader(HttpServletResponse response)
   {
      response.setContentType("text/html;charset=" + encoding);
   }

   @Override
   public void render()
   {
      String classpath = resolveClassPath();
      Template template = engine.create(classpath);
      Writable writable = template.make(map);
      try
      {
         writeHeader(response);
         PrintWriter writer = response.getWriter();
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

   @Override
   public void action()
   {
   }

}
