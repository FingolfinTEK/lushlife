package org.lushlife.negroni;

import java.lang.reflect.Type;
import java.util.List;

import javax.management.ObjectName;

import junit.framework.Assert;

import org.junit.Test;
import org.lushlife.negroni.util.Reflections;

public class ReflectionTest {

	static public class A<T> implements MixinInterface<T> {
		public void setMixinInstance(T instance) {
		}
	}

	static public class B extends A<Number> {
	}

	static public class C extends B {
	}

	static public class X implements MixinInterface<List<?>> {
		public void setMixinInstance(List<?> instance) {
		}
	}

	@Test
	public void testActualType() {
		Type mixinInstanceType = Reflections.getActualMixinType(A.class);
		Assert.assertEquals(null, mixinInstanceType);

		mixinInstanceType = Reflections.getActualMixinType(B.class);
		Assert.assertEquals(Number.class, mixinInstanceType);

		mixinInstanceType = Reflections.getActualMixinType(C.class);
		Assert.assertEquals(Number.class, mixinInstanceType);

	}

	@Test
	public void testWildCard() {
		Type mixinInstanceType = Reflections.getActualMixinType(X.class);
		Assert.assertEquals(List.class, mixinInstanceType);

	}

}
