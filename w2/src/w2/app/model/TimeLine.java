package w2.app.model;

import java.io.Serializable;

public class TimeLine implements Serializable {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
