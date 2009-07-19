package glassbottle2.extension.groovy.impl;
// package glassbottle.plugins.groovy.impl;
//
// import glassbottle.plugins.context.EventScopedMap;
// import glassbottle.plugins.context.NamingResolverMap;
// import glassbottle.plugins.context.scope.EventScoped;
// import glassbottle.plugins.controller.Controller;
// import glassbottle.plugins.controller.binding.ActiveController;
// import glassbottle.plugins.module.Application;
// import glassbottle.util.loader.ClassLoaderUtil;
// import glassbottle2.Injector;
// import groovy.lang.Closure;
// import groovy.text.Template;
//
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.LinkedList;
// import java.util.Map;
//
// import javax.enterprise.inject.Current;
//
// import org.jboss.webbeans.log.Log;
// import org.jboss.webbeans.log.Logging;
//
// @EventScoped
// public class TemplateManager
// {
//
// static Log logger = Logging.getLog(TemplateManager.class);
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
// @Current
// private EventScopedMap map;
//
// @Current
// private NamingResolverMap shellMap;
//
// protected Template loadTemplate(String contextName, String resourceName)
// {
// Controller controller = application.resolveController(contextName);
// return
// engine.create(ClassLoaderUtil.toClassPath(controller.getPackage().getName() +
// ".layout", resourceName));
//
// }
//
// public void include(String resourceName, Map<String, String> params, final
// Closure closure) throws IOException
// {
// include(controller.getPackage().getName(), resourceName, params, closure);
// }
//
// public void injectTo(String name, final Closure closure)
// {
// logger.info("injectTo {0}", name);
// TemplateHandler handler = template.getLast();
// handler.put(name, new PrintWriterDelegate(map)
// {
// @Override
// protected void invoke()
// {
// closure.call();
// }
// }.toString());
// }
//
// public void injectFrom(String name)
// {
// logger.info("injectFrom {0}", name);
// TemplateHandler handler = template.getLast();
// String value = handler.get(name);
// if (value != null)
// {
// getWriter().write(value);
// }
// }
//
// public String param(String name)
// {
// TemplateHandler handler = template.getLast();
// return handler.param(name);
// }
//
// public void include(String packageName, String resourceName, Map<String,
// String> params, final Closure closure) throws IOException
// {
// logger.info("include template [{0}],params [{1}]", resourceName, params);
//
// TemplateHandler tmp =
// injector.getInstanceByType(TemplateHandler.class).init(resourceName, params);
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
// this.template.pollLast();
// }
// }
//
// public PrintWriter getWriter()
// {
// return (PrintWriter) map.get("out");
// }
//
// }
