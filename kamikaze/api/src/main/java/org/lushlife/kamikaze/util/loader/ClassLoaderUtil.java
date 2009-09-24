package org.lushlife.kamikaze.util.loader;

import java.io.InputStream;

import org.lushlife.kamikaze.context.SingletonContext;

public class ClassLoaderUtil {
	public static boolean isHotdeployMode() {
		SingletonContext<?> context = org.lushlife.kamikaze.context.Contexts
				.getServletContext();
		String str = context.getServerInfo();
		if (!str.contains("Development")) {
			return false;
		}
		return true;
	}

	static public String toClassPath(String packageName, String resourceName) {
		return packageName.replace(".", "/") + "/" + resourceName;
	}

	static public AsStream load(ClassLoader loader, String packageName,
			String resoruceName) {
		String clazzPath = toClassPath(packageName, resoruceName);
		return load(loader, clazzPath);
	}

	public static AsStream load(ClassLoader loader, String clazzPath) {
		InputStream stream = loader.getResourceAsStream(clazzPath);
		if (stream == null) {
			throw new IllegalStateException("resoruce not found " + clazzPath);
		}
		return new AsStream(stream);
	}
}
