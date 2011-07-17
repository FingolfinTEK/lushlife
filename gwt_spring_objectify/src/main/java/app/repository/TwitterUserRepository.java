package app.repository;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.TwitterUser;

import com.googlecode.objectify.Objectify;

@Repository
public class TwitterUserRepository {

	@Inject
	Objectify objectify;

	@Transactional
	public void persist(TwitterUser user) {
		objectify.put(user);
	}

	public TwitterUser find(String name) {
		return objectify.find(TwitterUser.class, name);
	}
}
