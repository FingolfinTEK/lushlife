package w2.app.repository;

import twitter4j.TwitterFactory;
import twitter4j.conf.PropertyConfiguration;

public interface Twitter4J {

	static PropertyConfiguration conf = new PropertyConfiguration(Thread
			.currentThread().getContextClassLoader()
			.getResourceAsStream("twitter4j.properties"));

	static TwitterFactory factory = new TwitterFactory(conf);
}
