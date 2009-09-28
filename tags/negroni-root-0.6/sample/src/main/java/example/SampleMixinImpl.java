package example;

import org.lushlife.negroni.MixinInterface;
import org.lushlife.negroni.MixinMethod;

public class SampleMixinImpl implements SampleMixin, MixinInterface<Object> {
	Object obj;

	// mix-inの実装
	@MixinMethod
	public void mixin() {
		System.out.println("mixin " + obj);
	}

	// 実装クラスのインスタンスを取得する
	public void setMixinInstance(Object instance) {
		this.obj = instance;
	}

}
