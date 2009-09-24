package org.lushlife.negroni.annotation.literal;

import org.lushlife.negroni.annotation.Named;

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
