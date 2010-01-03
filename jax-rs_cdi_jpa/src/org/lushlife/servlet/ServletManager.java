package org.lushlife.servlet;

import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletManager {
	static public final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();

	static public final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();

	@Produces
	static public HttpServletRequest getRequest() {
		return REQUEST.get();
	}

	@Produces
	static public HttpServletResponse getResponse() {
		return RESPONSE.get();
	}

}
