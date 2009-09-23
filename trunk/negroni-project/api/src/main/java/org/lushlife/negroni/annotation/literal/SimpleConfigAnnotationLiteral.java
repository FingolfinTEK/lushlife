package org.lushlife.negroni.annotation.literal;

import org.lushlife.negroni.annotation.Config;

public class SimpleConfigAnnotationLiteral extends AnnotationLiteral<Config>
		implements Config {
	Class<?> type;
	boolean named;

	public SimpleConfigAnnotationLiteral(Class<?> type, boolean named) {
		this.type = type;
		this.named = named;
	}

	public boolean named() {
		return named;
	}

	public Class<?> type() {
		return type;
	}
}
