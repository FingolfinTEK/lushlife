package test;

import junit.framework.Assert;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.lushlife.guicexml.XmlModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.matcher.Matchers;

public class TestModule {

	@Test
	public void testModule() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				install(new XmlModule("components.xml"));
			}
		});
		SampleComponent test = injector.getInstance(Key
				.get(SampleComponent.class));
		SampleComponent test2 = injector.getInstance(Key
				.get(SampleComponent.class));

		System.out.println(test.a);
		Assert.assertEquals(test.a.size(), 3);
		Assert.assertEquals(test.b.size(), 2);
		Assert.assertEquals(test.c.size(), 2);
		System.out.println(test.b);
		System.out.println(test.c);
		Assert.assertNotNull(test.value);
		Assert.assertNotNull(test.value2);
		Assert.assertNotNull(test.value3);
		Assert.assertEquals(test, test2);

	}
}
