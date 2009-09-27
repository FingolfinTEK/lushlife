package org.lushlife.negroni.guice;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.lushlife.negroni.Container;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.MixinInstance;
import org.lushlife.negroni.Mixined;
import org.lushlife.negroni.Negroni;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class GuiceTest {

	@Singleton
	public static class Mixin {
		@Inject
		private AtomicInteger counter;

		public int getCount(@MixinInstance Sample sample) {
			return counter.get();
		}

		public int getCount(@MixinInstance Sample2 sample) {
			return counter.get();
		}

		public void methodMissing(@MixinInstance Object obj,
				@MissingMethod Method m, Object... args) {
			System.out.println("call " + m);
			counter.incrementAndGet();
		}

	}

	@Mixined(Mixin.class)
	public static interface Sample {
		public int getCount();

		public void invoke();
	}

	@Mixined(Mixin.class)
	public static interface Sample2 {
		public int getCount();

		public void invoke();
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

		Assert.assertEquals(2, sample.getCount());
		Assert.assertEquals(2, sample2.getCount());

	}

}
