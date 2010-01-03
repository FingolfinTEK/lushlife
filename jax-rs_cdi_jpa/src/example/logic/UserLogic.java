package example.logic;

import java.util.List;

import javax.inject.Inject;

import org.lushlife.BusinessLogic;

import example.dao.UserDao;
import example.model.User;

@BusinessLogic
public class UserLogic {
	@Inject
	UserDao dao;

	public void createUser(User user) {
		dao.createUser(user);
	}

	public void updateUser(User user) {
		dao.updateUser(user);
	}

	public List<User> getUsers() {
		return dao.getUsers();
	}

}
