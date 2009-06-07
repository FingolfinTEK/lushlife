package lushfile.plugins.view;

import java.util.Map.Entry;

import lushfile.core.context.RequestScopedMap;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class CopyOfViewUtilVinding {

	@Inject
	private RequestScopedMap map;

	@Inject
	private ViewUtilMetadata metaData;

	@Inject
	Injector injector;

	public void setup() {
		for (Entry<String, Class<?>> entry : metaData.getBindingItems()
				.entrySet()) {
			map.put(entry.getKey(), injector.getInstance(entry.getValue()));
		}

	}
}
