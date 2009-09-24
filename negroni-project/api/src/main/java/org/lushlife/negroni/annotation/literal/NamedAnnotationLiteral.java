package org.lushlife.negroni.annotation.literal;

import javax.enterprise.inject.AnnotationLiteral;
import javax.inject.Named;

public class NamedAnnotationLiteral extends AnnotationLiteral<Named> implements
		Named {
	String name;

	public NamedAnnotationLiteral(String name) {
		this.name = name;
	}

	public String value() {
		return name;
	}
}
