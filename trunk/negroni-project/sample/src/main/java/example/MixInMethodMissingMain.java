package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class MixInMethodMissingMain {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {

		Enhancer enhancer = Negroni.create();
		Class<? extends MixInMethodMissingImpl> enhancedClass = enhancer
				.enhace(MixInMethodMissingImpl.class);

		MixInMethodMissingImpl mixinSample = enhancedClass.newInstance();
		mixinSample.invoke();
	}
}
