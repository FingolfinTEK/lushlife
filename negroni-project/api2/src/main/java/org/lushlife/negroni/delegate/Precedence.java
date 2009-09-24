package org.lushlife.negroni.delegate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Precedence {
	int value();

}
