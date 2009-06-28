package negroni.sample;

import java.lang.reflect.Method;

import negroni.annotation.MethodMissing;
import negroni.annotation.Mixined;


public class AMixinImpl {

	@Mixined
	@MethodMissing
	public void method_missing(A a, Method m, Object... obj) {
		System.out.println(a + "\t" + m + "\t" + obj.length);
	}

}
