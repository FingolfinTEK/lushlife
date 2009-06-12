package lushfile.plugins.view.template;

import groovy.lang.Closure;
import groovy.text.Template;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import lushfile.core.LushLife;
import lushfile.core.context.RequestScopedMap;
import lushfile.core.groovy.ShellMap;
import lushfile.core.util.LushIO;
import lushfile.plugins.context.RequestContext;
import lushfile.plugins.view.GSPTemplateEngine;
import lushfile.plugins.view.PrintWriterDelegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class TemplateManager {

	static Logger logger = LoggerFactory.getLogger(TemplateManager.class);

	private LinkedList<TemplateHandler> template = new LinkedList<TemplateHandler>();

	@Inject
	private RequestContext context;

	@Inject
	private Injector injector;

	@Inject
	private GSPTemplateEngine engine;

	@Inject
	private RequestScopedMap map;

	@Inject
	private ShellMap shellMap;

	protected Template loadTemplate(String contextName, String resourceName) {
		String packageName = LushLife.resolvePackage(contextName);
		return engine.create(LushIO.toClassPath(packageName + ".layout",
				resourceName));

	}

	public void include(String resourceName, Map<String, String> params,
			final Closure closure) throws IOException {
		include(context.getPackageName(), resourceName, params, closure);
	}

	public void injectTo(String name, final Closure closure) {
		logger.info("injectTo {}", name);
		TemplateHandler handler = template.getLast();
		handler.put(name, new PrintWriterDelegate(map) {
			@Override
			protected void invoke() {
				closure.call();
			}
		}.toString());
	}

	public void injectFrom(String name) {
		logger.info("injectFrom {}", name);
		TemplateHandler handler = template.getLast();
		String value = handler.get(name);
		if (value != null) {
			getWriter().write(value);
		}
	}

	public String param(String name) {
		TemplateHandler handler = template.getLast();
		return handler.param(name);
	}

	public void include(String packageName, String resourceName,
			Map<String, String> params, final Closure closure)
			throws IOException {
		logger.info("include template [{}],params [{}]", resourceName, params);

		TemplateHandler tmp = injector.getInstance(TemplateHandler.class).init(
				resourceName, params);
		final Template template = loadTemplate(packageName, resourceName);
		this.template.add(tmp);
		try {
			new PrintWriterDelegate(map) {
				@Override
				protected void invoke() {
					closure.call();
				}
			}.run();
			template.make(shellMap).writeTo(getWriter());
		} finally {
			this.template.pollLast();
		}
	}

	public PrintWriter getWriter() {
		return (PrintWriter) map.get("out");
	}

}
