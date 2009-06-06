package lushfile.plugins.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

@Singleton
public class ViewUtilMetadata {

	private Map<String, Class<?>> bindingItems = new ConcurrentHashMap<String, Class<?>>();

	public Map<String, Class<?>> getBindingItems() {
		return bindingItems;
	}

}
