package lush.plugins.google;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GFunction {

	public boolean logined() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.isUserLoggedIn();
	}

	public User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	public UserService getUserService() {
		return UserServiceFactory.getUserService();
	}

}
