package lush.plugins.lfunction;

import groovy.lang.Closure;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lushfile.core.context.RequestScopedMap;
import lushfile.core.util.BeansUtil;
import lushfile.core.util.MapBuilder;
import lushfile.plugins.context.RequestContext;
import lushfile.plugins.resources.LoadScriptMetadata;
import lushfile.plugins.resources.ResourceManager;
import lushfile.plugins.view.PrintWriterDelegate;
import lushfile.plugins.view.template.TemplateManager;
import lushfile.plugins.view.util.Markup;
import lushfile.plugins.view.util.NestTag;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class LFunction {

	@Inject
	private RequestContext context;

	@Inject
	private RequestScopedMap requestScopedMap;

	@Inject
	@Named("commandKey")
	private String commandKey;

	@Inject
	private Provider<TemplateManager> manager;

	@Inject
	private Provider<ResourceManager> resourceManager;

	public void linkTo(String context, String action) {
		linkTo(context, action, new HashMap<String, Object>());
	}

	public void linkTo(String action) {
		linkTo(context.getContextName(), action, new HashMap<String, Object>());
	}

	public void linkTo(String action, Map<String, Object> attr) {
		linkTo(context.getContextName(), action, attr);
	}

	public void linkTo(String context, String action, Map<String, Object> attr) {
		final String value = buildLink(context, action, attr);
		getWriter().write(new Markup() {
		}.tag("a", attr, new NestTag() {

			@Override
			public void markup(Markup markup) {
				markup.text(value);
			}
		}).toString());
	}

	public void linkTo(String context, String action, Closure closure) {
		linkTo(context, action, new HashMap<String, Object>(), closure);
	}

	public void linkTo(String action, Closure closure) {
		linkTo(context.getContextName(), action, new HashMap<String, Object>(),
				closure);
	}

	public void linkTo(String action, Map<String, Object> attr, Closure closure) {
		linkTo(context.getContextName(), action, attr, closure);
	}

	public void linkTo(String context, String action, Map<String, Object> attr,
			final Closure closure) {
		buildLink(context, action, attr);
		getWriter().write(new Markup() {
		}.tag("a", attr, new NestTag() {
			@Override
			public void markup(Markup markup) {
				markup.text(new PrintWriterDelegate(requestScopedMap) {
					@Override
					protected void invoke() {
						closure.call();
					}
				}.toString());
			}
		}).toString());
	}

	private String buildLink(String context, String action,
			Map<String, Object> attr) {
		String[] str = action.split("\\.");
		String className = BeansUtil.toClassName(str[0]);
		String methodName = (str.length > 1) ? str[1] : null;

		String href = (methodName != null) ? //
		("/" + this.context.getBaseName() + "/" + context + "/" + className
				+ "/" + methodName)
				: //
				"/" + this.context.getBaseName() + "/" + context + "/"
						+ className;

		Object params = attr.remove("params");

		if (params != null) {
			if (params instanceof Map) {
				Map<?, ?> map = (Map<?, ?>) params;
				if (map.size() != 0) {
					StringBuilder sb = new StringBuilder();
					for (Entry<?, ?> en : map.entrySet()) {
						sb.append(en.getKey());
						sb.append("=");
						sb.append(en.getValue());
						sb.append("&");
					}
					sb.delete(sb.length() - 1, sb.length());
					href += "?" + sb.toString();
				}
			} else {
				href += "?" + params.toString();
			}
		}
		attr.put("href", href);

		final String value = (attr.containsKey("value")) ? (String) attr
				.remove("value") : action;
		return value;
	}

	public void image(String resource) {
		image(resource, new HashMap<String, String>());
	}

	public void image(String resource, Map<String, String> attr) {
		image(context.getContextName(), resource, new HashMap<String, String>());
	}

	public void image(String context, String resource) {
		image(context, resource, new HashMap<String, String>());
	}

	public void image(String context, String resource, Map<String, String> attr) {

		attr.put("src", resourceManager.get().createUrl(context, "image",
				resource));

		getWriter().write(new Markup().tag("img", attr).toString());

	}

	public void javascript(String resource) {
		javascript(resource, new HashMap<String, String>());
	}

	public void javascript(String resource, Map<String, String> attr) {
		javascript(context.getContextName(), resource,
				new HashMap<String, String>());
	}

	public void javascript(String context, String resource) {
		javascript(context, resource, new HashMap<String, String>());
	}

	public void javascript(String context, String resource,
			Map<String, String> attr) {

		if (!attr.containsKey("type")) {
			attr.put("type", "text/javascript");
		}

		attr.put("src", resourceManager.get().createUrl(context, "javascript",
				resource));

		getWriter().write(new Markup().tag("script", attr, new NestTag() {
			public void markup(Markup markup) {
			}
		}).toString());

	}

	public void css(String resource) {
		css(resource, new HashMap<String, String>());
	}

	public void css(String resource, Map<String, String> attr) {
		css(context.getContextName(), resource, new HashMap<String, String>());
	}

	public void css(String context, String resource) {
		css(context, resource, new HashMap<String, String>());
	}

	public void css(String context, String resource, Map<String, String> attr) {

		if (!attr.containsKey("type")) {
			attr.put("type", "text/css");
		}

		if (!attr.containsKey("rel")) {
			attr.put("rel", "Stylesheet");
		}

		if (!attr.containsKey("media")) {
			attr.put("media", "screen");
		}

		attr.put("href", resourceManager.get().createUrl(context, "css",
				resource));

		getWriter().write(new Markup().tag("link", attr).toString());

	}

	public void form(Closure closure) {
		form(new HashMap<String, String>(), closure);
	}

	private void form(Map<String, String> map, final Closure closure) {

		if (!map.containsKey("action")) {
			map.put("action", "/" + context.getBaseName() + "/"
					+ context.getContextName());
		}
		if (!map.containsKey("method")) {
			map.put("method", "post");
		}

		getWriter().append(new Markup().tag("form", map, new NestTag() {
			@Override
			public void markup(Markup markup) {
				markup.text(new PrintWriterDelegate(requestScopedMap) {
					protected void invoke() {
						closure.call();
					}
				}.run());
			}
		}).toString());
	}

	public PrintWriter getWriter() {
		return (PrintWriter) requestScopedMap.get("out");
	}

	public void hidden(String el) {
		hidden(el, new HashMap<String, String>());
	}

	public void hidden(String el, Map<String, String> params) {
		String type = "hidden";
		writeInput(el, params, type);
	}

	public void injectFrom(String name) {
		manager.get().injectFrom(name);
	}

	public void injectTo(String name, Closure closure) {
		manager.get().injectTo(name, closure);
	}

	public void input(String el) {
		input(el, new HashMap<String, String>());
	}

	public void input(String el, Map<String, String> params) {
		String type = "text";
		writeInput(el, params, type);
	}

	public void namespace(String contextname, Closure closure) {
		String oldContextName = context.getContextName();
		try {
			context.setContextName(contextname);
			closure.call();
		} finally {
			context.setContextName(oldContextName);
		}
	}

	public void param(String name) {
		getWriter().write(manager.get().param(name));
	}

	public void password(String el) {
		password(el, new HashMap<String, String>());
	}

	public void password(String el, Map<String, String> params) {
		String type = "secret";
		writeInput(el, params, type);
	}

	public void submit(String action) {
		submit(action, new HashMap<String, String>());
	}

	public void submit(String action, Map<String, String> params) {
		String type = "submit";
		if (!params.containsKey("id")) {
			params.put("id", action);
		}
		writeInput(commandKey + "##" + action, params, type);
	}

	public void template(String template, Closure closure) throws IOException {
		manager.get().include(template, new HashMap<String, String>(), closure);
	}

	public void template(String template, Map<String, String> map,
			Closure closure) throws IOException {
		manager.get().include(template, map, closure);
	}

	public void template(String packageName, String template, Closure closure)
			throws IOException {
		manager.get().include(packageName, template,
				new HashMap<String, String>(), closure);
	}

	public void template(String packageName, String template,
			Map<String, String> map, Closure closure) throws IOException {
		manager.get().include(packageName, template, map, closure);
	}

	private void writeInput(String el, Map<String, String> params, String type) {
		params.put("name", el);
		if (!params.containsKey("id")) {
			params.put("id", el);
		}
		if (!params.containsKey("type")) {
			params.put("type", type);
		}
		getWriter().append(new Markup().tag("input", params).toString());
	}

	@Inject
	LoadScriptMetadata metadata;

	public void loadScript(String key) {
		String src = metadata.getResources().get(key);
		getWriter().write(new Markup().tag("script", //
				new MapBuilder<String, String>()//
						.put("src", src)//
						.put("type", "text/script")//
						.toMap(), new NestTag() {
					@Override
					public void markup(Markup markup) {
					}
				}).toString());
	}
}
