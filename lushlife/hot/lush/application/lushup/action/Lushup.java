package lush.application.lushup.action;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import lush.application.lushup.model.Shop;
import lush.application.lushup.model.Shops;
import lush.application.lushup.model.TestDto;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;

public class Lushup {

	@Inject
	RequestContext parameter;

	@Inject
	TestDto dto;

	@Inject
	PersistenceManager persistenceManager;

	@Inject
	Shops shops;

	public void invoke() {
		Query query = persistenceManager.newQuery(Shop.class);
		Object obj = query.execute();
		shops.setShops((List<Shop>) obj);
	}

}
