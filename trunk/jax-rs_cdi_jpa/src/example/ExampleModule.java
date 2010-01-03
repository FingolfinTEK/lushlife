package example;

import org.lushlife.LushLifeModule;
import org.lushlife.inject.dsl.ModuleBase;

import example.action.UserAction;
import example.dao.UserDao;
import example.logic.UserLogic;
import example.model.User;


public class ExampleModule extends ModuleBase {

	public void configutaion() {
		include(new LushLifeModule());

		bean(ExampleConfiguration.class);

		bean(User.class);
		bean(UserAction.class);
		bean(UserLogic.class);
		bean(UserDao.class);

	}

}
