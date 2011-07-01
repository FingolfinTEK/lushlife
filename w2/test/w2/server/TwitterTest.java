package w2.server;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import twitter4j.Status;
import twitter4j.TwitterException;
import w2.GaeSpringRunner;
import w2.app.model.TimeLine;
import w2.app.repository.TimeLineRepo;
import w2.app.repository.TweetRepo;

@RunWith(GaeSpringRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/applicationContext*.xml")
public class TwitterTest {

	@Autowired
	TimeLineRepo dao;

	@Autowired
	TweetRepo tweetRepo;

	@Test
	public void testTimelineDao() throws TwitterException {
		List<TimeLine> timelines = dao.timeline("konkicci");
		for (TimeLine tl : timelines) {
			System.out.println(tl.getText());
		}
	}

	@Test
	public void testTweet() throws TwitterException {
		Status status = tweetRepo.tweet("‚Ä‚·‚Æ");
		System.out.println(status.getText());
	}

}
