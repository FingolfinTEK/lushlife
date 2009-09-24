package org.lushlife.negroni.impl;

import org.lushlife.negroni.Container;

public class SimpleContainer implements Container {

	public <T> T getInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
