package lushfile.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class LushContext {
	static private ThreadLocal<ServletContext> servletContext = new ThreadLocal<ServletContext>();
	static private ThreadLocal<Map<String, Object>> hiddenScope = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	public static Map<String, Object> getHiddenScope() {
		return hiddenScope.get();
	}

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
