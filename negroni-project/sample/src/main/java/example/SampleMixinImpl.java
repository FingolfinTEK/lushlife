package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.MixinInterface;
import org.lushlife.negroni.MixinMethod;

public class SampleMixinImpl implements SampleMixin, MixinInterface<Object> {
	Object obj;

	/**
	 * mix-inの実装
	 * 
	 * @param obj
	 */
	@MixinMethod
	public void mixin() {
		System.out.println("mixin " + obj);
	}

	/**
	 * mix-in & method-missingの実装
	 * 
	 * @param obj
	 * @param method
	 */
	@MixinMethod
	public void methodMissing(@MissingMethod Method method) {
		System.out.println("mixin and method missing " + obj + " : " + method);
	}

	public void setMixinInstance(Object instance) {
		this.obj = instance;
	}

}
