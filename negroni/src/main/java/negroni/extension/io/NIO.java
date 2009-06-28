package negroni.extension.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import negroni.annotation.MixinImplementedBy;
import negroni.core.closure.CV1;
import negroni.extension.io.impl.FileLIOImpl;
import negroni.extension.io.impl.StringLIOImpl;
import negroni.extension.io.impl.URLLIOImpl;


@MixinImplementedBy( { StringLIOImpl.class, FileLIOImpl.class, URLLIOImpl.class })
public interface NIO {

	void read(CV1<Reader> c);

	void write(CV1<Writer> c);

	void input(CV1<InputStream> c);

	void output(CV1<OutputStream> c);

}
