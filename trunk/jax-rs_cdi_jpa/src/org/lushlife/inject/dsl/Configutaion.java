package org.lushlife.inject.dsl;

import java.net.URL;

public interface Configutaion {

	void bean(Class<?> clazz);

	void include(Module module);

	void beansXml(URL url);

}
