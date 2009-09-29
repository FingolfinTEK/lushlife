package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.Mixin;
import org.lushlife.negroni.MixinMethod;

public class MethodMIssingMixinSample {
	@Mixin
	private Object obj;

	// mix-in & method-missingの実装
	@MixinMethod
	public void methodMissing(@MissingMethod Method method) {
		System.out.println("mixin and method missing " + obj + " : " + method);
	}

}
