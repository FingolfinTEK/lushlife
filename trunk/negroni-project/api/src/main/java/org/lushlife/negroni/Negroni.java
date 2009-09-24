package org.lushlife.negroni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.lushlife.negroni.core.exceptions.NegroniException;
import org.lushlife.negroni.core.impl.reflection.Reflections;

/**
 * The entry point to the Negroni framework. Creates {@link Enhancer}s from
 * classes.
 */
public class Negroni {

	/**
	 * Creates {@link Configurator} to create {@link Enhancer} managed by
	 * google-guice
	 * 
	 * @return
	 */
	public static Configurator config() {
		String resouceName = "META-INF/services/"
				+ Configurator.class.getName();
		URL url = Reflections.getResouce(resouceName);
		if (url == null) {
			// return new GuiceConfigurator();
		}
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String className = br.readLine();
			return (Configurator) Reflections.newInstnace(className);
		} catch (IOException e) {
			throw new NegroniException(e);
		}
	}

	public static Enhancer create() {
		return config().create();
	}
}
