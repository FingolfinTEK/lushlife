package lushfile.plugins.controller;

import javax.servlet.http.HttpServletRequest;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushEventManager;
import lushfile.plugins.controller.event.GetActionEvent;
import lushfile.plugins.controller.event.PostActionEvent;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class PackageLushController implements LushController {
	@Inject
	Injector injector;
	private String packageName;

	@Inject
	private Provider<HttpServletRequest> req;

	@Inject
	private Provider<LushEventManager> manager;

	public PackageLushController init(String packageName) {
		this.packageName = packageName.toLowerCase();
		return this;
	}

	@Override
	public void controll() {
		if (req.get().getMethod().toLowerCase().equals("get")) {
			manager.get().getEventQueue().add(
					injector.getInstance(GetActionEvent.class)
							.init(packageName));
		}
		if (req.get().getMethod().toLowerCase().equals("post")) {
			manager.get().getEventQueue().add(
					injector.getInstance(PostActionEvent.class).init(
							packageName));
		}
		fireEvent();
	}

	protected void fireEvent() {
		manager.get().fireEvents();
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public LushController init(Package pack) {
		return init(pack.getName());
	}
}
