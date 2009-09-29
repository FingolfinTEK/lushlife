package org.lushlife.negroni;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.lushlife.negroni.util.Reflections;
import org.lushlife.negroni.util.UnComparableException;

public class FieldCompoareTest {
	static public class TestF1 {
		Number n;
		CharSequence s;
	}

	static public class TestF2 {
		BigDecimal n;
		CharSequence s;
	}

	static public class TestF3 {
		Number n;
		String s;
	}

	@Test(expected = UnComparableException.class)
	public void test1() {
		int com = Reflections.compareField(TestF1.class.getDeclaredFields(),
				TestF1.class.getDeclaredFields());
		Assert.assertEquals(0, com);

		com = Reflections.compareField(TestF1.class.getDeclaredFields(),
				TestF2.class.getDeclaredFields());
		Assert.assertEquals(1, com);
		com = Reflections.compareField(TestF2.class.getDeclaredFields(),
				TestF1.class.getDeclaredFields());
		Assert.assertEquals(-1, com);

		com = Reflections.compareField(TestF2.class.getDeclaredFields(),
				TestF3.class.getDeclaredFields());
		System.out.println(com);
	}

	@Mixined( { NumberIntValue.class, BigDecimalIntValue.class })
	static public interface ToIntValue {
		int toIntValue();
	}

	static public class NumberIntValue implements ToIntValue {
		@Mixin
		Number number;
		static public boolean call = false;

		@MixinMethod
		public int toIntValue() {
			call = true;
			return number.intValue();
		}
	}

	static public class BigDecimalIntValue implements ToIntValue {
		@Mixin
		BigDecimal bigDecimal;
		static public boolean call = false;

		@MixinMethod
		public int toIntValue() {
			call = true;
			return bigDecimal.intValue();
		}
	}

	@Test
	public void testCompare() {
		Enhancer enhancer = Negroni.create();

		ToIntValue mixin = enhancer
				.mixin(ToIntValue.class, new BigDecimal(100));
		BigDecimalIntValue.call = false;
		mixin.toIntValue();
		Assert.assertTrue(BigDecimalIntValue.call);

		mixin = enhancer.mixin(ToIntValue.class, 100);
		NumberIntValue.call = false;
		mixin.toIntValue();
		Assert.assertTrue(NumberIntValue.call);
	}

}
