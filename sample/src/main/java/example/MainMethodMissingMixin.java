package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class MainMethodMissingMixin {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		Enhancer enhancer = Negroni.create();
		Class<? extends MethodMissingMixinedSample> enhancedClass = enhancer
				.enhace(MethodMissingMixinedSample.class);
		MethodMissingMixinedSample mixinSample = enhancedClass.newInstance();
		mixinSample.invoke();
	}
}
