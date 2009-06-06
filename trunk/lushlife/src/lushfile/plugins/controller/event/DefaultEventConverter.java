package lushfile.plugins.controller.event;

import com.google.inject.Inject;
import com.google.inject.Injector;

import lushfile.core.controller.ActionParameter;
import lushfile.core.controller.LushEvent;
import lushfile.core.controller.ReflectionErrorParameter;

public class DefaultEventConverter implements EventConverter {

	@Inject
	private Injector injector;

	@Override
	public LushEvent toEvent(ActionParameter returnValue) {
		if (returnValue.getFromAction() instanceof LushEvent) {
			return (LushEvent) returnValue.getFromAction();
		}
		return injector.getInstance(ViewEvent.class)//
				.init(returnValue);
	}

	@Override
	public LushEvent toEvent(ReflectionErrorParameter param) {
		return injector.getInstance(ExceptionEvent.class)//
				.init(param);
	}

}
