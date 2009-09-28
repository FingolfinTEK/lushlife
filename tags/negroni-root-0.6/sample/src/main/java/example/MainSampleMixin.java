package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class MainSampleMixin {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {

		Enhancer enhancer = Negroni.create();
		Class<? extends SampleMixined> enhancedClass = enhancer
				.enhace(SampleMixined.class);

		SampleMixined mixinSample = enhancedClass.newInstance();
		mixinSample.mixin();
	}

}
