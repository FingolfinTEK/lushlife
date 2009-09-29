package example;

import org.lushlife.negroni.Mixined;

@Mixined(SampleMixinImpl.class)
public interface SampleMixin {
	// mix-in メソッド
	public void mixin();

}
