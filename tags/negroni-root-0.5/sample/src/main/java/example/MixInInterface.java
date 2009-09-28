package example;

import org.lushlife.negroni.Mixined;

@Mixined(MixInSample.class)
public interface MixInInterface {
	// mix-in メソッド
	public void mixin();

}
