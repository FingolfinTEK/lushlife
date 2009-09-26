package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.MethodMissing;
import org.lushlife.negroni.Negroni;

public abstract class MethodMissingSample {
	@MethodMissing
	public void missing(Method method) {
		System.out.println(method);
	}

	public abstract void invoke();

}
