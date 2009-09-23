package org.lushlife.negroni.extension.io.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.lushlife.negroni.annotation.Mixined;
import org.lushlife.negroni.core.closure.CV1;



public class URLLIOImpl {

	@Mixined
	public void read(URL url, final CV1<Reader> c) throws IOException {
		input(url, new CV1<InputStream>() {
			public void call(InputStream outputStream) {
				c.call(new InputStreamReader(outputStream));
			}
		});
	}

	@Mixined
	public void write(URL url, final CV1<Writer> c) throws IOException {
		output(url, new CV1<OutputStream>() {
			public void call(OutputStream outputStream) {
				c.call(new OutputStreamWriter(outputStream));
			}

		});
	}

	@Mixined
	public void input(URL url, CV1<InputStream> c) throws IOException {
		InputStream is = null;
		try {
			is = url.openConnection().getInputStream();
			c.call(url.openStream());
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	@Mixined
	public void output(URL url, CV1<OutputStream> c) throws IOException {
		OutputStream os = null;
		try {
			os = url.openConnection().getOutputStream();
			c.call(os);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
}
