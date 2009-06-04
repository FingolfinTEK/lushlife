package lushfile.plugins.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public abstract class ReplaceOut {
	Map<String, Object> contextMap;

	public ReplaceOut(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
	}

	private Writer writer = new StringWriter();
	private PrintWriter pw = new PrintWriter(writer);

	abstract protected void invoke();

	boolean isRuned = false;

	public String run() {
		if (isRuned == false) {
			isRuned = true;
			Object replaced = contextMap.put("out", pw);
			try {
				invoke();
			} finally {
				contextMap.put("out", replaced);
			}
			pw.flush();
		}
		return writer.toString();
	}

	public String toString() {
		return run();
	}

}
