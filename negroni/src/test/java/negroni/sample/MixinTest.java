package negroni.sample;

import negroni.Enhancer;
import negroni.Negroni;

import org.junit.Test;


public class MixinTest {

	@Test
	public void a01() {
		Enhancer container = Negroni.config().add(A.class).create();
		A a = container.getInstance(A.class);
		a.invoke();
	}

}
