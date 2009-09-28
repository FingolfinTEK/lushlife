package org.lushlife.negroni;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class WrapdTest {

	@Mixined( { SizeString.class, SizeCollection.class })
	static public interface Size {
		public int size();
	}

	static public class SizeString implements Size, MixinInterface<String> {
		String instance;

		@MixinMethod
		public int size() {
			return instance.length();
		}

		public void setMixinInstance(String instance) {
			this.instance = instance;
		}
	}

	static public class SizeCollection implements Size,
			MixinInterface<Collection<?>> {
		Collection<?> instance;

		@MixinMethod
		public int size() {
			return instance.size();
		}

		public void setMixinInstance(Collection<?> instance) {
			this.instance = instance;
		}
	}

	@Test
	public void testWrap() {
		Enhancer enhancer = Negroni.create();
		String str = "xxx";
		Size size = enhancer.mixin(Size.class, str);
		Assert.assertEquals(str.length(), size.size());

		List<String> list = Arrays.asList("xxx", "vvv");
		size = enhancer.mixin(Size.class, list);
		Assert.assertEquals(list.size(), size.size());
	}
}
