package lushfile.core.context;

import javax.servlet.ServletContext;

public class LushContext {
	static private ThreadLocal<ServletContext> servletContext = new ThreadLocal<ServletContext>();

	public static ServletContext getServletContext() {
		ServletContext context = servletContext.get();
		if (context == null) {
			throw new IllegalStateException("ServletContext not initialized");
		}
		return context;
	}

	public static void setServletContext(ServletContext servletContext) {
		LushContext.servletContext.set(servletContext);
	}
}
