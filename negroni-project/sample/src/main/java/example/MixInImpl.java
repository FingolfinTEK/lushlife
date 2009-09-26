package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MethodMissing;
import org.lushlife.negroni.MixinMethod;

public class MixInImpl {

	@MixinMethod
	public void mixin(Object obj) {
		System.out.println("mixin " + obj);
	}

	@MixinMethod
	@MethodMissing
	public void methodMissing(Object obj, Method method) {
		System.out.println("mixin and method missing " + obj + " : " + method);
	}

}
