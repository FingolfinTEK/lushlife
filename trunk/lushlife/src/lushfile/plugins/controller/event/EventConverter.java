package lushfile.plugins.controller.event;

import com.google.inject.ImplementedBy;

import lushfile.core.controller.ActionParameter;
import lushfile.core.controller.LushEvent;
import lushfile.core.controller.ReflectionErrorParameter;

@ImplementedBy(DefaultEventConverter.class)
public interface EventConverter {

	LushEvent toEvent(ActionParameter returnValue);

	LushEvent toEvent(ReflectionErrorParameter reflection);
}
