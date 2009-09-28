package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MissingMethod;

public abstract class MethodMissingSample {

	public void methodMissing(@MissingMethod Method method) {
		System.out.println(method);
	}

	public abstract void invoke();

}
