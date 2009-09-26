package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class Main {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		Enhancer enhancer = Negroni.create();

		MethodMissingSample methodMissingSample = enhancer.enhace(
				MethodMissingSample.class).newInstance();
		methodMissingSample.invoke();

		MixinSample mixinSample = enhancer.enhace(MixinSample.class)
				.newInstance();
		mixinSample.mixin();
		mixinSample.invoke();
	}
}
