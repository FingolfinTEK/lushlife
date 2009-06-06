package lushfile.core.controller;

import java.util.Queue;

public interface LushEventManager {

	public void fireEvents();

	public Queue<LushEvent> getEventQueue();

}
