package lush.application.lushup.model;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class TestDto {

	String value = "action bean";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}


