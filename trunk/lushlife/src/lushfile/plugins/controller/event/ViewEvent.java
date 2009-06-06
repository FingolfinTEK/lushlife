package lushfile.plugins.controller.event;

import lushfile.core.controller.ActionParameter;
import lushfile.core.controller.LushEvent;
import lushfile.core.view.ViewHandler;

import com.google.inject.Inject;

public class ViewEvent implements LushEvent {

	@Inject
	private ViewHandler handler;

	private ActionParameter fromAction;

	@Inject
	lushfile.plugins.context.RequestContext context;

	public ViewEvent init(ActionParameter fromAction) {
		this.fromAction = fromAction;
		return this;
	}

	@Override
	public LushEvent fire() {
		String oldPackageName = context.getPackageName();
		try {
			context.setPackageName(fromAction.getPackageName());
			handler.handle(fromAction);
			return null;
		} finally {
			context.setPackageName(oldPackageName);
		}
	}

}
