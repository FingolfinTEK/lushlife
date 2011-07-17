package app.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import app.model.TwitterRequestToken;
import app.model.TwitterUser;

import com.google.appengine.api.users.UserService;
import com.googlecode.objectify.Objectify;

@Repository
@Singleton
public class TwitterRepository {
	@Inject
	TwitterFactory factory;

	@Inject
	Objectify objectify;

	@Inject
	UserService userService;

	public boolean isAuthorizationed(String name) {
		TwitterUser user = objectify.find(TwitterUser.class, name);
		if (user == null) {
			return false;
		}
		return true;
	}

	public TwitterRequestToken craeteRequestToken() {
		try {
			RequestToken token = factory.getInstance().getOAuthRequestToken();
			TwitterRequestToken requestToken = new TwitterRequestToken();
			requestToken.setToken(token.getToken());
			requestToken.setTokenSecret(token.getTokenSecret());
			requestToken.setAuthenticationUrl(token.getAuthenticationURL());
			return requestToken;
		} catch (TwitterException e) {
			// TODO handle exception
			throw new RuntimeException(e);
		}
	}

	public Twitter get() {
		if (userService.isUserLoggedIn()) {
			String name = userService.getCurrentUser().getUserId();
			TwitterUser twitterUser = objectify.find(TwitterUser.class, name);
			return get(twitterUser);
		} else {
			return factory.getInstance();
		}
	}

	public Twitter get(TwitterUser twitterUser) {
		AccessToken accessToken = new AccessToken(twitterUser.getToken(),
				twitterUser.getTokenSecret());
		return factory.getInstance(accessToken);
	}

	@Transactional
	public void verification(TwitterRequestToken token, String oauth_verifier) {
		try {
			AccessToken accessToken = factory
					.getInstance()
					.getOAuthAccessToken(token.toRequestToken(), oauth_verifier);
			TwitterUser user = new TwitterUser();
			user.setName(userService.getCurrentUser().getUserId());
			user.setToken(accessToken.getToken());
			user.setTokenSecret(accessToken.getTokenSecret());
			objectify.put(user);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
	}

}
