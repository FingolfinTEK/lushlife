package example;

import org.lushlife.negroni.Mixin;
import org.lushlife.negroni.MixinMethod;

public class SampleMixinImpl implements SampleMixin {
	@Mixin
	private Object obj;

	// mix-inの実装
	@MixinMethod
	public void mixin() {
		System.out.println("mixin " + obj);
	}

}
