package w2.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import w2.app.model.TimeLine;

@Repository
public class TimeLineRepo {

	public List<TimeLine> timeline(String user) throws TwitterException {
		Twitter twitter = Twitter4J.factory.getInstance();
		List<Status> statuses = twitter.getUserTimeline(user);
		List<TimeLine> timelines = new ArrayList<TimeLine>();
		for (Status status : statuses) {
			TimeLine timeline = new TimeLine();
			timeline.setText(status.getText());
			timelines.add(timeline);
		}
		return timelines;
	}

}
