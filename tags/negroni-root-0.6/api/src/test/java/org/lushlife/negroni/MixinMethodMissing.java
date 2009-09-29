package org.lushlife.negroni;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MixinMethodMissing {

	static public class Mixin implements MixinInterface<Object> {
		static public Object owner;
		static public String[] args;

		@MixinMethod
		public void xxx(@MissingMethod Method method, String str) {
			args = new String[] { str };
		}

		@MixinMethod
		public void xxx(@MissingMethod Method m, String... str) {
			args = str;
		}

		public void setMixinInstance(Object instance) {
			Mixin.owner = instance;
		}

	}

	@Mixined(Mixin.class)
	static public interface MixindClass {

		public void mixin(String str);

		public void mixin(String str, String str2);

	}

	Enhancer enhancer;

	@Before
	public void init() {
		this.enhancer = Negroni.create();
		Mixin.owner = null;
	}

	static public org.lushlife.negroni.Container __container;

	@Test
	public void testMixin() throws InstantiationException,
			IllegalAccessException {
		MixindClass newInstance = enhancer.enhace(MixindClass.class)
				.newInstance();
		newInstance.mixin("xxx");
		Assert.assertEquals(Mixin.args[0], "xxx");
	}

	@Test
	public void testMixin2() throws InstantiationException,
			IllegalAccessException {
		MixindClass newInstance = enhancer.enhace(MixindClass.class)
				.newInstance();
		newInstance.mixin("xxx", "yyy");
		Assert.assertEquals(Mixin.args[0], "xxx");
		Assert.assertEquals(Mixin.args[1], "yyy");

	}

}