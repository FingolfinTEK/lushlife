package lushfile.plugins.controller.event;

import lushfile.core.controller.LushEvent;

public class NullLushEvent implements LushEvent {

	@Override
	public LushEvent fire() {
		return null;
	}

}
