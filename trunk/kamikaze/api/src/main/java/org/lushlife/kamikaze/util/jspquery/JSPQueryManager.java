package org.lushlife.kamikaze.util.jspquery;

import java.util.LinkedList;

import org.lushlife.kamikaze.scope.EventScoped;
import org.lushlife.kamikaze.util.jspquery.tag.TagBase;

@EventScoped
public class JSPQueryManager {
	LinkedList<TagBase<?>> tags = new LinkedList<TagBase<?>>();

	public <T extends TagBase<T>> T tag(Class<T> clazz) {
		TagBase<?> tagBase = TagBase.of((Class<? extends TagBase>) clazz);
		tagBase.setManager(this);
		return (T) tagBase;
	}

	public void addStack(TagBase<?> tag) {
		tags.add(tag);
	}

	public String end() {
		TagBase<?> last = tags.removeLast();
		return last.end();
	}

}
