package lushfile.plugins.view.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Markup {

	StringBuilder sb;

	int indent;

	@Override
	public String toString() {
		return sb.toString();
	}

	public void indent() {
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
	}

	public Markup() {
		this(new StringBuilder(), 0);
	}

	public Markup(StringBuilder sb) {
		this(sb, 0);
	}

	public Markup(StringBuilder sb, int indent) {
		this.sb = sb;
		this.indent = indent;
	}

	public Markup text(String text) {
		sb.append(text);
		return this;
	}

	public Markup tag(String tag) {
		tag(tag, new HashMap<String, String>());
		return this;
	}

	public Markup tag(String tag, Map<String, ?> map) {
		sb.append("<" + tag);
		writeAttribute(map);
		sb.append(" />");
		return this;
	}

	public Markup tag(String tag, NestTag nestTag) {
		return tag(tag, new HashMap<String, String>(), nestTag);
	}

	public Markup tag(String tag, Map<String, ?> map, NestTag nestTag) {
		sb.append("<" + tag);
		writeAttribute(map);
		sb.append(">");
		indent++;
		indent();
		nestTag.markup(this);
		indent--;
		indent();
		sb.append("</" + tag + ">");
		return this;
	}

	private void writeAttribute(Map<String, ?> map) {
		for (Entry<String, ?> en : map.entrySet()) {
			sb.append(" ");
			sb.append(en.getKey());
			sb.append("=\"");
			sb.append(en.getValue());
			sb.append("\" ");
		}
	}
}
