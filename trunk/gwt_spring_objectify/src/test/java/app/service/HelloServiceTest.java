package app.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.query.client.css.PaddingProperty;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import app.model.TwitterUser;
import app.repository.TwitterRepository;
import ex.spring.GaeSpringRunner;

@RunWith(GaeSpringRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/application-context*.xml")
public class HelloServiceTest {

	@Inject
	TwitterRepository factory;

	@Test
	public void helloServiceTest() throws TwitterException {
		TwitterUser user = new TwitterUser();
		user.setToken("xxxx");
		user.setTokenSecret("xxxx");
		Twitter twitter = factory.get(user);
		ResponseList<Status> timeline;
		for (int i = 1; i < 2; i++) {
			Paging paging = new Paging(i);
			timeline = twitter.getHomeTimeline(paging);
			for (Status s : timeline) {
				System.out.println(s.getText());
			}
		}
	}

}
