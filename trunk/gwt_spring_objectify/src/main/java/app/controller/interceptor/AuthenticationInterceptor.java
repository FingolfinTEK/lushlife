package app.controller.interceptor;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import app.model.TwitterRequestToken;
import app.repository.TwitterRepository;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Inject
	TwitterRepository repository;
	@Inject
	Objectify objectify;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		try {
			UserService userService = UserServiceFactory.getUserService();
			if (!userService.isUserLoggedIn()) {
				String loginURL = userService.createLoginURL(request
						.getRequestURI());
				response.sendRedirect(loginURL);
				return false;
			}
			if (!repository.isAuthorizationed(userService.getCurrentUser()
					.getUserId())) {
				String oauth_token = request.getParameter("oauth_token");
				String oauth_verifier = request.getParameter("oauth_verifier");
				if (oauth_token != null && oauth_verifier != null) {
					TwitterRequestToken token = objectify.find(
							TwitterRequestToken.class, oauth_token);
					if (token != null) {
						repository.verification(token, oauth_verifier);
						return true;
					}
				}
				TwitterRequestToken token = repository.craeteRequestToken();
				token.setId(userService.getCurrentUser().getUserId());
				objectify.put(token);
				response.sendRedirect(token.getAuthenticationUrl());
				return false;
			}
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
