package negroni;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import negroni.extension.io.NIO;

import org.junit.Test;


public class LIOTest {

	@Test
	public void a01() throws MalformedURLException {
		Enhancer c = Negroni.create();
		String string = "test.txt";
		File file = new File(string);
		URL url = file.toURI().toURL();

		c.call(new $<NIO>(file) {
			public void write(Writer write) throws IOException {
				write.append("testtext");
			}
		});

		c.call(new $<NIO>(string) {
			public void read(Reader reader) throws IOException {
				BufferedReader br = new BufferedReader(reader);
				String s = br.readLine();
				Assert.assertEquals("testtext", s);
			}
		});

		c.call(new $<NIO>(file) {
			public void read(final Reader reader) throws IOException {
				BufferedReader br = new BufferedReader(reader);
				String s = br.readLine();
				Assert.assertEquals("testtext", s);
			}
		});

		c.call(new $<NIO>(url) {
			public void read(final Reader reader) throws IOException {
				BufferedReader br = new BufferedReader(reader);
				String s = br.readLine();
				Assert.assertEquals("testtext", s);
			}
		});
	}
}
