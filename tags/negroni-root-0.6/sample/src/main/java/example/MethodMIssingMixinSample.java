package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.MixinInterface;
import org.lushlife.negroni.MixinMethod;

public class MethodMIssingMixinSample implements MixinInterface<Object> {
	// mix-in & method-missingの実装
	@MixinMethod
	public void methodMissing(@MissingMethod Method method) {
		System.out.println("mixin and method missing " + obj + " : " + method);
	}

	private Object obj;

	// 実装クラスのインスタンスを取得
	public void setMixinInstance(Object instance) {
		this.obj = instance;
	}
}
