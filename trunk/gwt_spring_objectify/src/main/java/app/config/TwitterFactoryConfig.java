package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterFactoryConfig {

	@Bean
	public TwitterFactory createFactory() {
		twitter4j.conf.Configuration config = new ConfigurationBuilder()
				.setOAuthConsumerKey("y1pqieH6ikgj3vEE0MuACw")
				.setOAuthConsumerSecret(
						"DN2V5CdN4G8MM5eLPI6Klrnn1Yk38VvqzdJEx24ogic").build();
		return new TwitterFactory(config);
	}
}
