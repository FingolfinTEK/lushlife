package w2.extensions.gwt.spring;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SpringRemoteServiceServlet extends RemoteServiceServlet implements
		Controller {

	private static final long serialVersionUID = 2322524001269341192L;

	@Inject
	ServletContext context;

	@Override
	public ServletContext getServletContext() {
		return context;
	}

	@Override
	public String getServletName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		service(arg0, arg1);
		return null;
	}

}
