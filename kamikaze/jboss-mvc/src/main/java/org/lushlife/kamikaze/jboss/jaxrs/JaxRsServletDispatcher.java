package org.lushlife.kamikaze.jboss.jaxrs;

import java.io.IOException;
import java.util.Enumeration;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.lushlife.kamikaze.servlet.KamikazeServletDispatcher;

public class JaxRsServletDispatcher implements KamikazeServletDispatcher {
	HttpServletDispatcher dispatcher = new HttpServletDispatcher();

	@Inject
	public JaxRsServletDispatcher(final ServletContext context)
			throws ServletException {
		dispatcher.init(new ServletConfig() {

			public String getInitParameter(String arg0) {
				return context.getInitParameter(arg0);
			}

			public Enumeration<String> getInitParameterNames() {
				return context.getInitParameterNames();
			}

			public ServletContext getServletContext() {
				return context;
			}

			public String getServletName() {
				return JaxRsServletDispatcher.class.getName();
			}

		});

	}

	public void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		dispatcher.service(arg0, arg1);
	}

}
