package org.lushlife.kamikaze;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KamikazeContext {

	static private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	static private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	static private ThreadLocal<ServletContext> servletContext = new ThreadLocal<ServletContext>();
	static private ThreadLocal<Map<Contextual<? extends Object>, Object>> hiddenScope = new ThreadLocal<Map<Contextual<? extends Object>, Object>>() {
		@Override
		protected Map<Contextual<? extends Object>, Object> initialValue() {
			return new HashMap<Contextual<? extends Object>, Object>();
		}
	};

	public static Map<Contextual<? extends Object>, Object> getHiddenScope() {
		return hiddenScope.get();
	}

	@Named("request")
	@Produces
	public static HttpServletRequest getRequest() {
		return request.get();
	}

	public static void setRequest(HttpServletRequest request) {
		KamikazeContext.request.set(request);
	}

	@Named("response")
	@Produces
	public static HttpServletResponse getResponse() {
		return response.get();
	}

	public static void setResponse(HttpServletResponse response) {
		KamikazeContext.response.set(response);
	}

	@Produces
	public static ServletContext getServletContext() {
		ServletContext context = servletContext.get();
		return context;
	}

	public static void setServletContext(ServletContext servletContext) {
		KamikazeContext.servletContext.set(servletContext);
	}

	public static <T> T get(Class<T> clazz) {
		return (T) KamikazeContext.getServletContext().getAttribute(
				clazz.getName());
	}

	public static <T> void setRegistries(Class<T> clazz, T instance) {
		KamikazeContext.getServletContext().setAttribute(clazz.getName(),
				instance);
	}
}
