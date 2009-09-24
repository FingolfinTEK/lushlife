package org.lushlife.kamikaze.context;

import java.net.MalformedURLException;
import java.net.URL;

public interface SingletonContext<T> extends Context<T> {

	URL getResource(String string) throws MalformedURLException;

	String getServerInfo();

}
