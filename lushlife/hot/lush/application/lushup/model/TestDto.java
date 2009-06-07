package lush.application.lushup.model;

import java.io.Serializable;

import lushfile.core.guice.HiddenScoped;

@HiddenScoped
public class TestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value = "action bean";

	private int intValue;

	public void increment() {
		intValue++;
	}

	public String getValue() {
		return value + ":" + intValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
