package lushfile.plugins.controller;

import java.util.PriorityQueue;
import java.util.Queue;

import lushfile.core.controller.LushEvent;
import lushfile.core.controller.LushEventManager;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class DefaultLushEventManager implements LushEventManager {
	private Queue<LushEvent> lushEvent = new PriorityQueue<LushEvent>();

	@Override
	public void fireEvents() {
		while (!lushEvent.isEmpty()) {
			LushEvent event = lushEvent.poll();
			LushEvent chainEvent = event.fire();
			if (chainEvent != null) {
				lushEvent.add(chainEvent);
			}
		}
	}

	@Override
	public Queue<LushEvent> getEventQueue() {
		return lushEvent;
	}

}
