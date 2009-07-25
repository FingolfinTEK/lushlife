package glassbottle2.extension.groovy.impl;

import glassbottle2.el.ELVariableEventScopedMap;
import glassbottle2.scope.EventScoped;

import java.io.PrintWriter;

import javax.enterprise.inject.Current;

import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

@EventScoped
public class TemplateManager
{

   static Log logger = Logging.getLog(TemplateManager.class);

   //
   // private LinkedList<TemplateHandler> template = new
   // LinkedList<TemplateHandler>();
   //
   // @Current
   // private Injector injector;
   //
   // @Current
   // private glassbottle.plugins.groovy.GSPTemplateEngine engine;
   //
    @Current
   private ELVariableEventScopedMap map;
   //
   // @Current
   //   private NamingResolverMap shellMap;

   // protected Template loadTemplate(String resourceName)
   // {
   // return engine.create(resourceName);
   //
   // }
   //
   // public void include(String resourceName, Map<String, String> params, final
   // Closure closure) throws IOException
   // {
   // include(controller.getPackage().getName(), resourceName, params, closure);
   //   }

//   public void injectTo(String name, final Closure closure)
//   {
//      logger.info("injectTo {0}", name);
//      TemplateHandler handler = template.getLast();
//      handler.put(name, new PrintWriterDelegate(map)
//      {
//         @Override
//         protected void invoke()
//         {
//            closure.call();
//         }
//      }.toString());
//   }
//
//   public void injectFrom(String name)
//   {
//      logger.info("injectFrom {0}", name);
//      TemplateHandler handler = template.getLast();
//      String value = handler.get(name);
//      if (value != null)
//      {
//         getWriter().write(value);
//      }
//   }
//
//   public String param(String name)
//   {
//      TemplateHandler handler = template.getLast();
//      return handler.param(name);
//   }

   // public void include(String packageName, String resourceName, Map<String,
   // String> params, final Closure closure) throws IOException
   // {
   // logger.info("include template [{0}],params [{1}]", resourceName, params);
   //
   // TemplateHandler tmp =
   // injector.getInstanceByType(TemplateHandler.class).init(resourceName,
   // params);
   // final Template template = loadTemplate(packageName, resourceName);
   // this.template.add(tmp);
   // try
   // {
   // new PrintWriterDelegate(map)
   // {
   // @Override
   // protected void invoke()
   // {
   // closure.call();
   // }
   // }.run();
   // template.make(shellMap).writeTo(getWriter());
   // }
   // finally
   // {
   //         this.template.pollLast();
   //      }
   //   }

   public PrintWriter getWriter()
   {
      return (PrintWriter) map.get("out");
   }

}
