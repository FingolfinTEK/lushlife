package example.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.lushlife.DataAccess;

import example.model.User;

@DataAccess
public class UserDao {

	@Inject
	EntityManager entityManager;

	public List<User> getUsers() {
		return entityManager.createQuery("select u from User u")
				.getResultList();
	}

	public User reserveUserId() {
		User user = new User();
		entityManager.persist(user);
		return user;
	}

	public void createUser(User user) {
		entityManager.persist(user);
	}

	public void updateUser(User user) {
		entityManager.merge(user);
	}

}
