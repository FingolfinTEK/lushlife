package example;

import org.lushlife.negroni.Mixined;

@Mixined(MixInImpl.class)
public interface MixInInterface {
	/**
	 * mix-in メソッド
	 */
	public void mixin();

	/**
	 * method-missing
	 */
	public void invoke();
}
