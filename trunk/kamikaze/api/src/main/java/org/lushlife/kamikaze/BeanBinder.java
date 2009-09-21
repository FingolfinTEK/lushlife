package org.lushlife.kamikaze;

import java.net.URL;

public interface BeanBinder {
	public void install(BeanModule module);

	public void model(Class<?> clazz);

	public void beansXml(URL url);
}
