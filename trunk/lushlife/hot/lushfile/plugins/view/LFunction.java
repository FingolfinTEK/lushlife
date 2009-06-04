package lushfile.plugins.view;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lushfile.core.context.RequestScopedMap;
import lushfile.plugins.context.RequestContext;
import lushfile.plugins.view.util.Markup;
import lushfile.plugins.view.util.NestTag;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LFunction {

	@Inject
	HttpServletRequest request;

	@Inject
	RequestContext context;

	@Inject
	RequestScopedMap requestScopedMap;

	@Inject
	@Named("commandKey")
	String commandKey;

	public PrintWriter getWriter() {
		return (PrintWriter) requestScopedMap.get("out");
	}

	public void form(Closure closure) {
		form(new HashMap<String, String>(), closure);
	}

	private void form(Map<String, String> map, final Closure closure) {
		// System.out.println(request.getRequestURI());
		// System.out.println(request.getPathInfo());
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
				markup.text(new ReplaceOut(requestScopedMap) {
					protected void invoke() {
						closure.call();
					}
				}.run());
			}
		}).toString());
	}

	public void input(String el) {
		input(el, new HashMap<String, String>());
	}

	public void input(String el, Map<String, String> params) {
		String type = "text";
		writeInput(el, params, type);
	}

	public void hidden(String el) {
		hidden(el, new HashMap<String, String>());
	}

	public void hidden(String el, Map<String, String> params) {
		String type = "hidden";
		writeInput(el, params, type);
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

	public String link_to(Map<String, String> param) {
		return "";
	}

}
