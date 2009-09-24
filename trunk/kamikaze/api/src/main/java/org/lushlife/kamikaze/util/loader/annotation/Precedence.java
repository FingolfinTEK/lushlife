package org.lushlife.kamikaze.util.loader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Precedence {
	int value();

	static public int BUILD_IN = 10;
	static public int FRAMEWORK = 20;
	static public int APPLICTION = 30;
	static public int DEPLOYMENT = 40;
	static public int MOCK = 50;

}
