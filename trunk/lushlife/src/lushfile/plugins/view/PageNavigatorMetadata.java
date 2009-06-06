package lushfile.plugins.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

@Singleton
public class PageNavigatorMetadata {

	Map<String, PageNavigator> navigators = new ConcurrentHashMap<String, PageNavigator>();

	public Map<String, PageNavigator> getNavigators() {
		return navigators;
	}

}
