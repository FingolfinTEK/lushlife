package lushfile.plugins.controller.event;

import lushfile.core.controller.LushEvent;

public class NullEvent implements LushEvent {

	@Override
	public LushEvent fire() {
		return null;
	}

}
