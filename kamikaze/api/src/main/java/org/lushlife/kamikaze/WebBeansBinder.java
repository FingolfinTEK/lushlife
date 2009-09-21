package org.lushlife.kamikaze;

import java.net.URL;

public interface WebBeansBinder {
	public void install(WebBeansModule module);

	public void installService(Class<? extends WebBeansModule> clazz);

	public void model(Class<?> clazz);

	public void beansXml(URL url);

}
