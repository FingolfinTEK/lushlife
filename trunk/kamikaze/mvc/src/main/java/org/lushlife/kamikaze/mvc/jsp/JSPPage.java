package org.lushlife.kamikaze.mvc.jsp;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.lushlife.kamikaze.Kamikaze;
import org.lushlife.kamikaze.LogMsgKMKZC;
import org.lushlife.kamikaze.mvc.view.Page;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class JSPPage implements Page {
	static Log log = Logging.getLog(JSPPage.class);

	static public Page to(String path) {
		return Kamikaze.getInjector().getInstance(JSPPage.class).path(path);
	}

	private String path;

	public String getPath() {
		return path;
	}

	@Inject
	@Named("request")
	private HttpServletRequest reqeust;

	@Inject
	@Named("response")
	private HttpServletResponse response;

	@Inject
	private ServletContext context;

	public JSPPage path(String path) {
		this.path = path;
		return this;
	}

	public void write(OutputStream arg0) throws IOException,
			WebApplicationException {
		try {
			log.log(LogMsgKMKZC.KMKZC0001, path);
			response.reset();
			context.getRequestDispatcher(path).include(reqeust, response);
		} catch (ServletException e) {
			throw new WebApplicationException(e);
		}
	}

}
