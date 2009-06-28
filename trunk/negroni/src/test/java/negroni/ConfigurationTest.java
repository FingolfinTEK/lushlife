package negroni;

import negroni.annotation.Config;
import junit.framework.Assert;


public class ConfigurationTest {
	static public class TestBean {
		String name;
		TestBean bean;

		public TestBean getBean() {
			return bean;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static public class TestBean2 {
		String name;
		TestBean bean;

		public TestBean getBean() {
			return bean;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public enum TestConf {
		@Config(type = TestBean.class, named = true)
		refs {
			String name = "cccc";
		}
	}

	public enum TestConf2 {
		@Config(type = TestBean2.class, named = true)
		testBean {
			String name = "bbbb";
			Enum<?> bean = TestConf.refs;
		}
	}

	@org.junit.Test
	public void a01() {
		Enhancer container = Negroni.config().configuration(TestConf.class)
				.configuration(TestConf2.class).create();
		TestBean2 bean = (TestBean2) container.getInstance(TestConf2.testBean
				.name());
		Assert.assertEquals("bbbb", bean.getName());
		Assert.assertEquals("cccc", bean.getBean().getName());

	}
}