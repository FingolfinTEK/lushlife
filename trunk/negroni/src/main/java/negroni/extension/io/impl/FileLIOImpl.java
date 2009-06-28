package negroni.extension.io.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import negroni.annotation.Mixined;
import negroni.core.closure.CV1;


public class FileLIOImpl {

	@Mixined
	public void read(File f, CV1<Reader> c) throws IOException {
		FileReader reader = null;
		try {
			reader = new FileReader(f);
			c.call(reader);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

	}

	@Mixined
	public void write(File f, CV1<Writer> c) throws IOException {
		Writer writer = null;
		try {
			writer = new FileWriter(f);
			c.call(writer);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	@Mixined
	public void input(File f, CV1<InputStream> c) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
			c.call(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	@Mixined
	public void output(File f, CV1<OutputStream> c) throws IOException {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(f);
			c.call(outputStream);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
