package example;

import org.lushlife.negroni.MixinInstance;

public class MixInSample {
	// mix-inの実装
	public void mixin(@MixinInstance Object obj) {
		System.out.println("mixin " + obj);
	}
}
