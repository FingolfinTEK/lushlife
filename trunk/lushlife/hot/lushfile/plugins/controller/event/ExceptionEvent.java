package lushfile.plugins.controller.event;

import lushfile.core.controller.LushEvent;
import lushfile.core.controller.ReflectionErrorParameter;
import lushfile.core.view.ErrorViewHandler;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;

public class ExceptionEvent implements LushEvent {

	private ReflectionErrorParameter error;

	@Inject
	private ErrorViewHandler handler;
	@Inject
	RequestContext context;

	@Override
	public LushEvent fire() {
		String oldPackage = context.getPackageName();
		try {
			String packageNage = error.getPackageName();
			context.setContextName(packageNage);
			handler.handle(error);
			return null;
		} finally {
			context.setPackageName(oldPackage);
		}
	}

	public LushEvent init(ReflectionErrorParameter param) {
		this.error = param;
		return this;
	}

}
