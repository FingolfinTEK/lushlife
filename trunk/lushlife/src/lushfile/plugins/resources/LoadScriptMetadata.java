package lushfile.plugins.resources;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

@Singleton
public class LoadScriptMetadata {
	private Map<String, String> resources = new ConcurrentHashMap<String, String>();

	public Map<String, String> getResources() {
		return resources;
	}

	public void setResources(Map<String, String> resources) {
		this.resources = resources;
	}

}
