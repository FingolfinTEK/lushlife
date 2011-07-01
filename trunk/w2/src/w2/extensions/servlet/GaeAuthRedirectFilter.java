package w2.extensions.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * A servlet filter that handles basic GAE user authentication.
 */
public class GaeAuthRedirectFilter implements Filter {

	private String domain = "";
	private String errorPage = "authentication_error.jsp";

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (!userService.isUserLoggedIn()) {
			String requestUrl = request.getHeader("requestUrl");
			if (requestUrl == null) {
				requestUrl = request.getRequestURI();
			}
			response.sendRedirect(userService.createLoginURL(requestUrl));
			return;
		}
		if (!userService.isUserAdmin()
				&& !userService.getCurrentUser().getEmail().endsWith(domain)) {
			servletRequest.getRequestDispatcher(errorPage).include(
					servletRequest, servletResponse);
			response.setContentType("text/html");
			return;
		}

		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig config) {
	}
}
