package org.lushlife.mvc;

import java.util.ArrayList;
import java.util.Iterator;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Messages implements Iterable<Message> {
	java.util.List<Message> messages = new ArrayList<Message>();

	public Iterator<Message> iterator() {
		return messages.iterator();
	}

	public void addMessage(String message) {
		messages.add(new Message(message));
	}

}
