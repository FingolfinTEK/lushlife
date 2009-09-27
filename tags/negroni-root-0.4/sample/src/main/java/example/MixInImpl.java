package example;

import java.lang.reflect.Method;

import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.MixinInstance;

public class MixInImpl {

	/**
	 * mix-inの実装
	 * 
	 * @param obj
	 */
	public void mixin(@MixinInstance Object obj) {
		System.out.println("mixin " + obj);
	}

	/**
	 * mix-in & method-missingの実装
	 * 
	 * @param obj
	 * @param method
	 */
	public void methodMissing(@MixinInstance Object obj,
			@MissingMethod Method method) {
		System.out.println("mixin and method missing " + obj + " : " + method);
	}

}
