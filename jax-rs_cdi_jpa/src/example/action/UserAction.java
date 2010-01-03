package example.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.lushlife.PresentationLogic;
import org.lushlife.mvc.ActionBase;
import org.lushlife.mvc.JSPPage;
import org.lushlife.stla.Log;

import example.ExampleLog;
import example.logic.UserLogic;
import example.model.User;

@ManagedBean
@PresentationLogic
@Path("/user")
public class UserAction extends ActionBase {

	@Inject
	private Log log;

	@Inject
	private UserLogic userLogic;

	@Inject
	private User user;

	@Inject
	private Validator validator;

	@GET
	public JSPPage users() {
		List<User> users = userLogic.getUsers();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", users);
		return forward("/users.jsp", map);
	}

	@GET
	@Path("/new")
	public JSPPage newUser() {
		log.log(ExampleLog.NEW_USER);
		userLogic.createUser(user);
		return forward("/newUser.jsp").bind("user", user);
	}

	@POST
	@Path("/new")
	public JSPPage updateUser(@FormParam("name") String userName) {
		log.log(ExampleLog.UPDATE_USER);
		user.setName(userName);

		Set<ConstraintViolation<User>> validtionResult = validator
				.validate(user);

		if (!validtionResult.isEmpty()) {
			return forward("/newUser.jsp").bind("user", user)
					.addValidationMessage(validtionResult);
		}
		userLogic.updateUser(user);
		return users();
	}
}
