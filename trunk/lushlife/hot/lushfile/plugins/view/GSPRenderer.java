package lushfile.plugins.view;

import groovy.lang.Writable;
import groovy.text.Template;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import lushfile.core.groovy.ShellMap;
import lushfile.core.view.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class GSPRenderer implements Renderer {

	Logger logger = LoggerFactory.getLogger(GSPRenderer.class);

	@Inject
	private GSPTemplateEngine engine;

	private String classLoaderPath;

	@Inject
	private HttpServletResponse response;

	@Inject
	ImplicitBinder binder;

	@Inject
	ShellMap shellMap;

	@Override
	public void render() throws IOException {
		writerHeader(response);
		Template template = engine.create(classLoaderPath);
		binder.setup();
		Writable writable = template.make(shellMap);
		writable.writeTo(response.getWriter());
		if (logger.isDebugEnabled()) {
			logger.debug("response " + writable);
		}
	}

	private void writerHeader(HttpServletResponse response) {
		response.setContentType("text/html;charset=utf8");
	}

	@Override
	public GSPRenderer init(String viewResouces) {
		this.classLoaderPath = viewResouces;
		return this;
	}
}
