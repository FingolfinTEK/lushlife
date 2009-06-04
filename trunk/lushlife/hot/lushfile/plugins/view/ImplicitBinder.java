package lushfile.plugins.view;

import java.io.IOException;

import groovy.xml.MarkupBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lushfile.core.context.RequestScopedMap;
import lushfile.plugins.template.TemplateBuilder;

import com.google.inject.Inject;

public class ImplicitBinder {

	@Inject
	RequestScopedMap map;

	@Inject
	HttpServletRequest request;

	@Inject
	HttpServletResponse response;

	@Inject
	TemplateBuilder template;

	@Inject
	LFunction l;

	public void setup() {
		try {
			map.put("request", request);
			map.put("response", response);
			map.put("session", request.getSession());
			map.put("context", request.getSession().getServletContext());
			map.put("application", request.getSession().getServletContext());
			map.put("html", new MarkupBuilder(response.getWriter()));
			map.put("template", template);
			map.put("l", l);
		} catch (IOException e) {
			// TODO ERROR Handling
			throw new RuntimeException(e);
		}

	}

}
