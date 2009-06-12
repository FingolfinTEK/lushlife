package lushfile.core.util;

import java.io.InputStream;

public class LushIO {

	static public String toClassPath(String packageName, String resourceName) {
		return packageName.replace(".", "/") + "/" + resourceName;
	}

	static public AsStream load(ClassLoader loader, String packageName,
			String resoruceName) {
		String clazzPath = toClassPath(packageName, resoruceName);
		InputStream stream = loader.getResourceAsStream(clazzPath);
		if (stream == null) {
			// TODO Error handling
			throw new IllegalStateException("resoruce not found " + clazzPath);
		}
		return new AsStream(stream);
	}
}
