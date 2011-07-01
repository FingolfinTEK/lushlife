package w2.app.repository;

import org.springframework.stereotype.Repository;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

@Repository
public class TweetRepo {

	public Status tweet(String message) throws TwitterException {
		AccessToken token = new AccessToken(
				"317056955-79vsiJrpm7vPscr9czEoLvEVwtki9hZ9nYpcBjut",
				"5Aa4zvgiiQgsw0qgUUqXVOtCJq0vquLWgIAbsfuZ8");
		Twitter twitter = Twitter4J.factory.getInstance(token);
		Status status = twitter.updateStatus(message);
		return status;
	}
}
