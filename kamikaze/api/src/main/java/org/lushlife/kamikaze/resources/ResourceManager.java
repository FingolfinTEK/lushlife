package org.lushlife.kamikaze.resources;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringEscapeUtils;
import org.lushlife.kamikaze.context.ServletPathInfo;
import org.lushlife.kamikaze.extension.jspquery.JSPQueryManager;
import org.lushlife.kamikaze.extension.jspquery.tag.CSS;
import org.lushlife.kamikaze.extension.jspquery.tag.JavaScript;
import org.lushlife.kamikaze.scope.EventScoped;
import org.lushlife.kamikaze.util.date.StartupTime;

@EventScoped
public class ResourceManager {
	@Inject
	@StartupTime
	private Long startuptime;

	@Inject
	private ServletPathInfo info;

	@Inject
	private JSPQueryManager jsp;

	public CSS css(Class<?> resourceClass, String resource) throws IOException {
		CSS css = jsp.$(CSS.class);
		css.href(toUrl(resourceClass, "css", resource));
		return css;
	}

	public JavaScript js(Class<?> resourceClass, String resource)
			throws IOException {
		JavaScript javaScript = jsp.$(JavaScript.class);
		javaScript.src(toUrl(resourceClass, "js", resource));
		javaScript.encoding(encoding);
		return javaScript;
	}

	@Inject
	@Named("encoding")
	private String encoding;

	private String toUrl(Class<?> resourceClass, String type, String resource) {
		return url(resourceClass) + "/" + resource + "/" + type + "?"
				+ startuptime;
	}

	public AddPath url(Class<?> resourceClass) {
		return new AddPath().path(info.getServletPath() + "/"
				+ resourceClass.getAnnotation(Path.class).value());

	}

	public String h(Object object) {
		return StringEscapeUtils.escapeHtml(String.valueOf(object));
	}
}
