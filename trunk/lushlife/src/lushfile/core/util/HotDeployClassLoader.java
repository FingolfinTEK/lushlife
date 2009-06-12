package lushfile.core.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class HotDeployClassLoader extends URLClassLoader {

	public HotDeployClassLoader(URL[] urls, ClassLoader parent,
			URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}

	public HotDeployClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public HotDeployClassLoader(URL[] urls) {
		super(urls);
	}

}
