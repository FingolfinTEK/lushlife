package lushfile.plugins.resources;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushEventManager;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class ResourceController implements LushController {

	@Inject
	Provider<LushEventManager> eventManager;

	@Inject
	Injector injector;

	@Override
	public void controll() {
		eventManager.get().getEventQueue().add(
				injector.getInstance(ResoruceEvent.class));
		eventManager.get().fireEvents();
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	String packageName;

	@Override
	public LushController init(String packageName) {
		this.packageName = packageName;
		return this;
	}

	@Override
	public LushController init(Package pack) {
		return init(pack.getName());
	}

}
