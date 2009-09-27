package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class Main {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		/**
		 * Enhancerの取得
		 */
		Enhancer enhancer = Negroni.create();

		/**
		 * method-missing
		 */
		MethodMissingSample methodMissingSample = enhancer.enhace(
				MethodMissingSample.class).newInstance();
		methodMissingSample.invoke();

		/**
		 * mix-in
		 */
		MixinSample mixinSample = enhancer.enhace(MixinSample.class)
				.newInstance();
		mixinSample.mixin();
		mixinSample.invoke();
	}
}
