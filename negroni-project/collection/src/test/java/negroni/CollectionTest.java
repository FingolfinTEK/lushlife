package negroni;

import java.util.List;

import junit.framework.Assert;


import org.junit.Test;
import org.lushlife.negroni.$;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.extension.collection.EnumerableArrayList;


public class CollectionTest {

	@Test
	public void a01() {
		Enhancer con = Negroni.create();
		java.util.List<String> list = con.getInstance(EnumerableArrayList.class);
		list.add("s1");
		list.add("s1");

		con.call(new $(list) {
			public void each(String s) {
				Assert.assertEquals("s1", s);
			}
		});
		list.clear();
		list.add("sss");
		list.add("sab");
		list.add("ssss");
		List<String> c = (List<String>) con.call(new $(list) {
			public boolean findAll(String s) {
				return s.length() == 3;
			}
		});
		con.call(new $(list) {
			public boolean findAll(String s) {
				return s.length() == 3;
			}
		});
		Assert.assertEquals(2, c.size());
		Assert.assertEquals("sss", c.get(0));
		Assert.assertEquals("sab", c.get(1));

		String str = (String) con.call(new $(list) {
			public boolean find(String s) {
				return s.length() == 4;
			}
		});
		Assert.assertEquals("ssss", str);
	}
}
