package lushfile.core.sample;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

public class TestServlet extends HttpServlet {

	org.slf4j.Logger logger = LoggerFactory.getLogger(TestServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext context = req.getSession().getServletContext();
		URL url = context.getResource("/WEB-INF/modules/");
		logger.info("url " + url);
		URLClassLoader loader = new URLClassLoader(new URL[] { url }, Thread
				.currentThread().getContextClassLoader());

		String loadClass = "lushfile.plugins.controller.event.NullLushEvent";

		logger.info("ClassLoader#resource(String) "
				+ loader.getResource(loadClass.replace(".", "/") + ".class"));
		try {
			Class<?> clazz = loader.loadClass(loadClass);
			logger.info("load success " + clazz);
		} catch (ClassNotFoundException e) {
			logger.info("load failed ", e);
		}
	}
}
