package org.lushlife.negroni;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MixinTest {

	static public class MixinSample {
		@Mixin
		static public Object owner;
		static public String[] args;

		@MixinMethod
		public void mixin(String str) {
			args = new String[] { str };
		}

		@MixinMethod
		public void mixin(String... str) {
			args = str;
		}

		public void setMixinInstance(Object instance) {
			MixinSample.owner = instance;
		}
	}

	@Mixined(MixinSample.class)
	static public interface MixindClass {

		public void mixin(String str);

		public void mixin(String str, String str2);

	}

	private Enhancer enhancer;

	@Before
	public void init() {
		this.enhancer = Negroni.create();
		MixinSample.owner = null;
	}

	@Test
	public void testMixin() throws InstantiationException,
			IllegalAccessException {
		MixindClass newInstance = enhancer.enhace(MixindClass.class)
				.newInstance();
		newInstance.mixin("xxx");
		Assert.assertEquals(MixinSample.args[0], "xxx");
	}

	@Test
	public void testMixin2() throws InstantiationException,
			IllegalAccessException {
		MixindClass newInstance = enhancer.enhace(MixindClass.class)
				.newInstance();
		newInstance.mixin("xxx", "yyy");
		Assert.assertEquals(MixinSample.args[0], "xxx");
		Assert.assertEquals(MixinSample.args[1], "yyy");

	}

}
