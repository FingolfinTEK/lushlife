package org.lushlife.negroni;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MethodMissingTest {

	static abstract public class EnhanceClass {
		static public boolean call = false;
		static public Object args;

		abstract public void invoke();

		abstract public void invoke(Integer str);

		abstract public void invoke(String str);

		abstract public void invoke(String... str);

		public void methodMissing(@MissingMethod Method method) {
			call = true;
		}

		public void methodMissing(@MissingMethod Method method, Integer integer) {
			call = true;
			args = integer;
		}

		public void methodMissing(@MissingMethod Method method, String integer) {
			call = true;
			args = integer;
		}

		public void methodMissing(@MissingMethod Method method, String... str) {
			call = true;
			args = str;
		}
	}

	@Before
	public void init() {
		EnhanceClass.call = false;
		EnhanceClass.args = null;
		ehnahcer = Negroni.create();
	}

	Enhancer ehnahcer;

	@Test
	public void methodMissingTest() throws InstantiationException,
			IllegalAccessException {
		EnhanceClass instance = ehnahcer.enhace(EnhanceClass.class)
				.newInstance();
		instance.invoke();
		Assert.assertTrue(EnhanceClass.call);
		Assert.assertNull(EnhanceClass.args);
	}

	@Test
	public void methodMissingTest2() throws InstantiationException,
			IllegalAccessException {
		EnhanceClass instance = ehnahcer.enhace(EnhanceClass.class)
				.newInstance();
		instance.invoke(10);
		Assert.assertTrue(EnhanceClass.call);
		Assert.assertEquals(10, EnhanceClass.args);
	}

	@Test
	public void methodMissingTest3() throws InstantiationException,
			IllegalAccessException {
		EnhanceClass instance = ehnahcer.enhace(EnhanceClass.class)
				.newInstance();
		instance.invoke("str");
		Assert.assertTrue(EnhanceClass.call);
		Assert.assertEquals("str", EnhanceClass.args);
	}

	@Test
	public void methodMissingTest4() throws InstantiationException,
			IllegalAccessException {
		EnhanceClass instance = ehnahcer.enhace(EnhanceClass.class)
				.newInstance();
		instance.invoke("str", "strs");
		Assert.assertTrue(EnhanceClass.call);
		Assert.assertEquals("str", ((Object[]) EnhanceClass.args)[0]);
		Assert.assertEquals("strs", ((Object[]) EnhanceClass.args)[1]);
	}
}
