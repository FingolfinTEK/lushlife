package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.MixinInstance;

public class MixInMethodMissingSample {
	// mix-in & method-missingの実装
	public void methodMissing(@MixinInstance Object obj,
			@MissingMethod Method method) {
		System.out.println("mixin and method missing " + obj + " : " + method);
	}
}
