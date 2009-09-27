package org.lushlife.negroni.jsr299;

import javax.inject.Singleton;

import org.junit.Assert;
import org.junit.Test;
import org.lushlife.kamikaze.CoreModule;
import org.lushlife.kamikaze.Injector;
import org.lushlife.kamikaze.Kamikaze;
import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.negroni.Container;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.MixinInstance;
import org.lushlife.negroni.Mixined;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.impl.DelegateContainer;

public class JSR299Test {

	@Singleton
	static public class TestMixIn {
		static public String str;

		public void invoke(@MixinInstance Object obj, String str) {
			TestMixIn.str = str;
		}

		int count = 0;

		public int count(@MixinInstance Object obj) {
			return ++count;
		}
	}

	@Mixined(TestMixIn.class)
	static public interface Invoker {
		public void invoke(String str);

		public int count();
	}

	@Mixined(TestMixIn.class)
	static public interface Invoker2 {
		public void invoke(String str);

		public int count();
	}

	static public class TestModule implements WebBeansModule {

		Enhancer enhancer;

		public TestModule(Enhancer enhancer) {
			super();
			this.enhancer = enhancer;
		}

		public void configure(WebBeansBinder binder) {
			binder.install(new CoreModule());
			binder.model(enhancer.enhace(Invoker.class));
			binder.model(enhancer.enhace(Invoker2.class));
			for (Class<?> mixin : Negroni.mixinCLasses(Invoker.class)) {
				binder.model(mixin);
			}

		}

	}

	@Test
	public void testWebBeans() {
		Container container = new DelegateContainer() {
			@Override
			protected Container delegate() {
				return new BeanManagerContainer(Kamikaze.getBeanManager());
			}
		};
		Enhancer enhancer = Negroni.create(container);
		Kamikaze.bootInjector(new TestModule(enhancer));
		Injector injector = Kamikaze.getInjector();
		injector.getInstance(Invoker.class).invoke("xxx");
		injector.getInstance(Invoker2.class).count();

		Assert.assertEquals("xxx", TestMixIn.str);
		Assert.assertEquals(2, injector.getInstance(Invoker.class).count());

	}

}
