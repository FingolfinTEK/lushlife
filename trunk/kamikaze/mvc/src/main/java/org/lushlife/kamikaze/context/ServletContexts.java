package org.lushlife.kamikaze.context;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletContexts {

	static private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	static private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	@Produces
	public static HttpServletRequest getRequest() {
		return request.get();
	}

	@Named("request")
	@Produces
	public static HttpServletRequest getRequestWithName() {
		return request.get();
	}

	public static void setRequest(HttpServletRequest request) {
		ServletContexts.request.set(request);
	}

	@Produces
	public static HttpServletResponse getResponse() {
		return response.get();
	}

	@Named("response")
	@Produces
	public static HttpServletResponse getResponseWithName() {
		return response.get();
	}

	public static void setResponse(HttpServletResponse response) {
		ServletContexts.response.set(response);
	}

	@Produces
	public static ServletContext getServletContext() {
		SingletonContext<?> servletContext = Contexts.getServletContext();
		if (servletContext == null) {
			return null;
		}
		return (ServletContext) servletContext.getDelegate();
	}

	public static void setServletContext(ServletContext servletContext) {
		if (servletContext == null) {
			Contexts.setServletContext(null);
		}
		Contexts.setServletContext(new SingletonContextImpl(servletContext));
	}

}
