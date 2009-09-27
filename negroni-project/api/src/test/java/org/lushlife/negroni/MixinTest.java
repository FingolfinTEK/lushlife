package org.lushlife.negroni;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MixinTest {

	static public class Mixin {
		static public Object owner;
		static public String[] args;

		public void mixin(@MixinInstance Object owner, String str) {
			Mixin.owner = owner;
			args = new String[] { str };
		}

		public void mixin(@MixinInstance Object owner, String... str) {
			Mixin.owner = owner;
			args = str;
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
