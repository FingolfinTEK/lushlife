package negroni;

import junit.framework.Assert;

import org.junit.Test;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Mix;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.core.closure.CV;


public class ClouserTest {

	static public class TestC {
		public void times(int time, CV c) {
			while (time-- > 0) {
				c.call();
			}
		}
	}

	@Test
	public void a01() {
		final int[] count = new int[1];
		count[0] = 0;
		Enhancer c = Negroni.create();
		c.call(new Mix<TestC>(new Object(), 2) {
			public void times() {
				count[0]++;
			}
		});
		Assert.assertEquals(2, count[0]);
	}
}
