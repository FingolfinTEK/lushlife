package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Configuration
public class UserServiceConfig {

	@Bean
	public UserService get() {
		return UserServiceFactory.getUserService();
	}

}
