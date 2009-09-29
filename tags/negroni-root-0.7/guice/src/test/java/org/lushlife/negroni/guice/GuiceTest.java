package org.lushlife.negroni.guice;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.Mixin;
import org.lushlife.negroni.MixinMethod;
import org.lushlife.negroni.Mixined;
import org.lushlife.negroni.Negroni;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class GuiceTest {

	@Singleton
	public static class MixinMethodMIssing {

		@Mixin
		private Object obj;

		@Inject
		private AtomicInteger counter;

		@MixinMethod
		public int getCounter() {
			return counter.get();
		}

		@MixinMethod
		public void methodMissing(@MissingMethod Method m, Object... args) {
			System.out.println("call " + m + " mixin " + obj);
			counter.incrementAndGet();
		}
	}

	@Mixined(MixinMethodMIssing.class)
	abstract public static class Sample {
		@Inject
		public Sample(AtomicInteger atomic) {
			System.out.println(atomic);
		}

		abstract public Integer getCounter();

		abstract public void invoke();

		abstract public int[] invoke2();
	}

	@Mixined(MixinMethodMIssing.class)
	abstract public static class Sample2 {
		abstract public Integer getCounter();

		abstract public void invoke();
	}

	@Test
	public void testGuice() {
		Injector injector = Guice.createInjector();
		GuiceContainer container = new GuiceContainer(injector);

		Enhancer enhancer = Negroni.create(container);
		Class<? extends Sample> sam = enhancer.enhace(Sample.class);

		Class<? extends Sample2> sam2 = enhancer.enhace(Sample2.class);

		Sample sample = injector.getInstance(sam);
		Sample2 sample2 = injector.getInstance(sam2);
		sample.invoke();
		sample2.invoke();

		Assert.assertEquals((Integer) 2, sample.getCounter());
		Assert.assertEquals((Integer) 2, sample2.getCounter());
		sample.invoke2();

	}

}
