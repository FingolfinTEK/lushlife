package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class MixInMain {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {

		Enhancer enhancer = Negroni.create();
		Class<? extends MixInedSample> enhancedClass = enhancer
				.enhace(MixInedSample.class);

		MixInedSample mixinSample = enhancedClass.newInstance();
		mixinSample.mixin();
	}

}
