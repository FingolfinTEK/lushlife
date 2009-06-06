package lushfile.core.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;

@Singleton
public class LushControllerMetadata {
	Map<String, LushController> packageMapping = new HashMap<String, LushController>();

	public Map<String, LushController> getPackageMapping() {
		return packageMapping;
	}

}
