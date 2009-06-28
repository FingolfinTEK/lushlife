package negroni.extension.io.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import negroni.annotation.Mixined;
import negroni.core.closure.CV1;


public class StringLIOImpl {
	FileLIOImpl fileLIOImpl = new FileLIOImpl();

	@Mixined
	public void read(String file, CV1<Reader> c) throws IOException {
		fileLIOImpl.read(new File(file), c);
	}

	@Mixined
	public void write(String file, CV1<Writer> c) throws IOException {
		fileLIOImpl.write(new File(file), c);
	}

	@Mixined
	public void input(String file, CV1<InputStream> c) throws IOException {
		fileLIOImpl.input(new File(file), c);
	}

	@Mixined
	public void output(String file, CV1<OutputStream> c) throws IOException {
		fileLIOImpl.output(new File(file), c);
	}

}
