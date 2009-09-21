package org.lushlife.kamikaze.util.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.nio.channels.IllegalSelectorException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.lushlife.kamikaze.util.LogMsgUtil;
import org.lushlife.stla.Logging;

public class ServiceLoader<S> implements Iterable<S> {

	private static final String SERVICES = "META-INF/services/";

	public static <S> ServiceLoader<S> load(Class<S> service) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return load(service, loader);
	}

	public static <S> ServiceLoader<S> load(Class<S> service, ClassLoader loader) {
		if (loader == null) {
			loader = service.getClassLoader();
		}
		return new ServiceLoader<S>(service, loader);
	}

	private final String serviceFile;
	private Class<S> expectedType;
	private final ClassLoader loader;

	private Set<S> providers;

	private ServiceLoader(Class<S> service, ClassLoader loader) {
		this.loader = loader;
		this.serviceFile = SERVICES + service.getName();
		this.expectedType = service;
	}

	private void reload() {
		providers = new HashSet<S>();
		Enumeration<URL> enumeration = null;
		boolean errorOccurred = false;

		try {
			enumeration = loader.getResources(serviceFile);
		} catch (IOException ioe) {
			errorOccurred = true;
		}

		if (!errorOccurred) {
			while (enumeration.hasMoreElements()) {
				try {
					final URL url = enumeration.nextElement();
					final InputStream is = url.openStream();
					final BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));

					String line = reader.readLine();
					while (null != line) {
						final int comment = line.indexOf('#');

						if (comment > -1) {
							line = line.substring(0, comment);
						}

						line.trim();

						if (line.length() > 0) {
							try {
								Class<?> clazz = loader.loadClass(line);
								Class<? extends S> serviceClass;
								try {
									serviceClass = clazz
											.asSubclass(expectedType);
								} catch (ClassCastException e) {
									throw new IllegalStateException(
											"Extension "
													+ line
													+ " does not implement Extension");
								}
								Constructor<? extends S> constructor = serviceClass
										.getConstructor();
								S instance = constructor.newInstance();
								providers.add(instance);
							} catch (NoClassDefFoundError e) {
								throw new IllegalStateException(Logging
										.getMessage(LogMsgUtil.REAPERUTL00002,
												line));
							} catch (InstantiationException e) {
								throw new IllegalStateException(Logging
										.getMessage(LogMsgUtil.REAPERUTL00002,
												line));
							} catch (IllegalAccessException e) {
								throw new IllegalStateException(Logging
										.getMessage(LogMsgUtil.REAPERUTL00002,
												line));
							}
						}

						line = reader.readLine();
					}
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	public Iterator<S> iterator() {
		if (providers == null) {
			reload();
		}
		return providers.iterator();
	}

	public S getSingle() {
		if (providers == null) {
			reload();
		}
		if (providers.size() == 0) {
			throw new IllegalStateException("not found " + expectedType);
		}
		if (providers.size() >= 2) {
			throw new IllegalStateException("ambiguity " + expectedType);
		}

		return providers.iterator().next();

	}

	public String toString() {
		return "Services for " + serviceFile;
	}

}
