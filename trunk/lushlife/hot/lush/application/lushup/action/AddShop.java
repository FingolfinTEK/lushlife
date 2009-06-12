package lush.application.lushup.action;

import javax.jdo.PersistenceManager;

import lush.application.lushup.model.Shop;
import lush.plugins.model.ModelUpdater;
import lushfile.core.jdo.Transactional;

import com.google.inject.Inject;

public class AddShop {

	@Inject
	PersistenceManager manager;

	public String invoke() {
		try {
			Shop shop = updater.update(Shop.class);
			// shop.setName("default name");
			return "/add_shop.gsp";
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Inject
	ModelUpdater updater;

	@Inject
	Lushup lushup;

	public String persist() {
		final Shop shop = updater.update(Shop.class);
		new Transactional(manager) {
			@Override
			protected void transactional() {
				manager.makePersistent(shop);
			}
		}.invoke();
		
		lushup.invoke();
		return "/index.gsp";
	}

}
