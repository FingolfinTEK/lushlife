package glassbottle2.extension.groovy;
// package glassbottle.plugins.groovy;
//
// import glassbottle.plugins.context.ActionContext;
// import glassbottle.plugins.context.EventScopedMap;
// import glassbottle.plugins.context.HiddenContextManager;
// import glassbottle.plugins.context.Namespace;
// import glassbottle.plugins.context.scope.EventScoped;
// import glassbottle.plugins.controller.CommandManager;
// import glassbottle.plugins.groovy.impl.PrintWriterDelegate;
// import glassbottle.plugins.groovy.impl.TemplateManager;
// import glassbottle.plugins.resources.ResourceManager;
// import glassbottle.util.beans.BeansUtil;
// import glassbottle.util.builder.MapBuilder;
// import glassbottle.util.markup.Markup;
// import glassbottle.util.markup.NestTag;
// import groovy.lang.Closure;
//
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Map.Entry;
//
// import javax.enterprise.inject.Current;
// import javax.enterprise.inject.Instance;
// import javax.enterprise.inject.Named;
//
//
// import org.apache.commons.lang.StringEscapeUtils;
//
//
// @Named("l")
// @EventScoped
// public class LFunction
// {
// @Current
// ActionContext context;
//
// @Current
// private EventScopedMap eventScopedMap;
//
// @Current
// Namespace namespace;
//
// @Current
// CommandManager commandManager;
//
// @Current
// private Instance<TemplateManager> manager;
//
// @Current
// private Instance<ResourceManager> resourceManager;
//
// public void linkTo(Map<String, Object> attr)
// {
// linkTo(namespace.getName(), attr);
// }
//
// public void linkTo(String context, Map<String, Object> attr)
// {
// final String href = buildLink(context, attr);
// attr.put("href", href);
// final String value = String.valueOf(attr.remove("value"));
// getWriter().write(new glassbottle.util.markup.Markup()
// {
// }.tag("a", attr, new glassbottle.util.markup.NestTag()
// {
//
// @Override
// public void markup(glassbottle.util.markup.Markup markup)
// {
// markup.text(value);
// }
// }).toString());
// }
//
// public void linkTo(Map<String, Object> attr, Closure closure)
// {
// linkTo(namespace.getName(), attr, closure);
// }
//
// public void linkTo(String context, Map<String, Object> attr, final Closure
// closure)
// {
// String href = buildLink(context, attr);
// attr.put("href", href);
// getWriter().write(new Markup()
// {
// }.tag("a", attr, new NestTag()
// {
// @Override
// public void markup(Markup markup)
// {
// markup.text(new PrintWriterDelegate(eventScopedMap)
// {
// @Override
// protected void invoke()
// {
// closure.call();
// }
// }.toString());
// }
// }).toString());
//
// }
//
// private String buildLink(String context, Map<String, Object> attr)
// {
// Object view = attr.remove("view");
// String href;
// if (view != null)
// {
// href = "/" + this.context.getServletPath() + "/" + context + "/" +
// BeansUtil.toClassName(String.valueOf(view));
// }
// else
// {
// href = "/" + this.context.getServletPath() + "/" + context;
// }
// Map<Object, Object> map = new HashMap<Object, Object>();
// String action = (String) attr.remove("action");
// if (action != null)
// {
// String actionKey = commandManager.toActionKey(action);
// map.put(actionKey, actionKey);
// }
// Object params = attr.remove("params");
// if (params != null)
// {
// if (params instanceof Map)
// {
// map.putAll((Map<?, ?>) params);
// }
// }
// if (map.size() != 0)
// {
// StringBuilder sb = new StringBuilder();
// for (Entry<?, ?> en : map.entrySet())
// {
// sb.append(en.getKey());
// sb.append("=");
// sb.append(en.getValue());
// sb.append("&");
// }
// sb.delete(sb.length() - 1, sb.length());
// href += "?" + sb.toString();
// }
// return href;
// }
//
// public void image(String resource)
// {
// image(resource, new HashMap<String, String>());
// }
//
// public void image(String resource, Map<String, String> attr)
// {
// image(namespace.getName(), resource, new HashMap<String, String>());
// }
//
// public void image(String context, String resource)
// {
// image(context, resource, new HashMap<String, String>());
// }
//
// public void image(String context, String resource, Map<String, String> attr)
// {
//
// attr.put("src", resourceManager.get().createUrl(context, "image", resource));
//
// getWriter().write(new Markup().tag("img", attr).toString());
//
// }
//
// public void javascript(String resource)
// {
// javascript(resource, new HashMap<String, String>());
// }
//
// public void javascript(String resource, Map<String, String> attr)
// {
// javascript(namespace.getName(), resource, new HashMap<String, String>());
// }
//
// public void javascript(String context, String resource)
// {
// javascript(context, resource, new HashMap<String, String>());
// }
//
// public void javascript(String context, String resource, Map<String, String>
// attr)
// {
//
// if (!attr.containsKey("type"))
// {
// attr.put("type", "text/javascript");
// }
//
// attr.put("src", resourceManager.get().createUrl(context, "javascript",
// resource));
//
// getWriter().write(new Markup().tag("script", attr, new NestTag()
// {
// public void markup(Markup markup)
// {
// }
// }).toString());
//
// }
//
// public void css(String resource)
// {
// css(resource, new HashMap<String, String>());
// }
//
// public void css(String resource, Map<String, String> attr)
// {
// css(namespace.getName(), resource, new HashMap<String, String>());
// }
//
// public void css(String context, String resource)
// {
// css(context, resource, new HashMap<String, String>());
// }
//
// public void css(String context, String resource, Map<String, String> attr)
// {
//
// if (!attr.containsKey("type"))
// {
// attr.put("type", "text/css");
// }
//
// if (!attr.containsKey("rel"))
// {
// attr.put("rel", "Stylesheet");
// }
//
// if (!attr.containsKey("media"))
// {
// attr.put("media", "screen");
// }
//
// attr.put("href", resourceManager.get().createUrl(context, "css", resource));
//
// getWriter().write(new Markup().tag("link", attr).toString());
//
// }
//
// public void form(Closure closure)
// {
// form(new HashMap<String, String>(), closure);
// }
//
// @Current
// HiddenContextManager hidden;
//
// private void form(Map<String, String> map, final Closure closure)
// {
//
// if (!map.containsKey("action"))
// {
// map.put("action", "/" + context.getServletPath() + "/" +
// namespace.getName());
// }
// if (!map.containsKey("method"))
// {
// map.put("method", "post");
// }
//
// getWriter().append(new Markup().tag("form", map, new NestTag()
// {
// @Override
// public void markup(Markup markup)
// {
// markup.tag("input", //
// new MapBuilder().put("type", "hidden")//
// .put("name", hidden.getHiddenKey())//
// .put("value", hidden.toHidden()).toMap());
//
// markup.text(new PrintWriterDelegate(eventScopedMap)
// {
// protected void invoke()
// {
// closure.call();
// }
// }.run());
// }
// }).toString());
// }
//
// @Deprecated
// public PrintWriter getWriter()
// {
// return this.manager.get().getWriter();
// }
//
// public void hidden(String el)
// {
// hidden(el, new HashMap<String, String>());
// }
//
// public void hidden(String el, Map<String, String> params)
// {
// String type = "hidden";
// writeInput(el, params, type);
// }
//
// public void injectFrom(String name)
// {
// manager.get().injectFrom(name);
// }
//
// public void injectTo(String name, Closure closure)
// {
// manager.get().injectTo(name, closure);
// }
//
// public void input(String el)
// {
// input(el, new HashMap<String, String>());
// }
//
// public void input(String el, Map<String, String> params)
// {
// String type = "text";
// writeInput(el, params, type);
// }
//
// public void namespace(String contextname, Closure closure)
// {
// String oldContextName = namespace.getName();
// try
// {
// namespace.setName(contextname);
// closure.call();
// }
// finally
// {
// namespace.setName(oldContextName);
// }
// }
//
// public void param(String name)
// {
// String param = manager.get().param(name);
// if (param != null)
// {
// getWriter().write(param);
// }
// else
// {
// getWriter().write("#param[" + name + "]");
// }
// }
//
// public void password(String el)
// {
// password(el, new HashMap<String, String>());
// }
//
// public void password(String el, Map<String, String> params)
// {
// String type = "secret";
// writeInput(el, params, type);
// }
//
// public void submit(String action)
// {
// submit(action, new HashMap<String, String>());
// }
//
// public void submit(String action, Map<String, String> params)
// {
// String type = "submit";
// if (!params.containsKey("id"))
// {
// params.put("id", action);
// }
// writeInput(commandManager.toActionKey(action), params, type);
// }
//
// public void template(Closure closure) throws IOException
// {
// template(new HashMap<String, String>(), closure);
// }
//
// public void template(Map<String, String> params, Closure closure) throws
// IOException
// {
// template("template.gsp", params, closure);
// }
//
// public void template(String template, Closure closure) throws IOException
// {
// manager.get().include(template, new HashMap<String, String>(), closure);
// }
//
// public void template(String template, Map<String, String> map, Closure
// closure) throws IOException
// {
// manager.get().include(template, map, closure);
// }
//
// public void template(String packageName, String template, Closure closure)
// throws IOException
// {
// manager.get().include(packageName, template, new HashMap<String, String>(),
// closure);
// }
//
// public void template(String packageName, String template, Map<String, String>
// map, Closure closure) throws IOException
// {
// manager.get().include(packageName, template, map, closure);
// }
//
// private void writeInput(String el, Map<String, String> params, String type)
// {
// params.put("name", el);
// if (!params.containsKey("id"))
// {
// params.put("id", el);
// }
// if (!params.containsKey("type"))
// {
// params.put("type", type);
// }
// getWriter().append(new Markup().tag("input", params).toString());
// }
//
// public String h(Object object)
// {
// return StringEscapeUtils.escapeHtml(String.valueOf(object));
// }
// }
