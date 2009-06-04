package lushfile.core.controller;

import java.util.HashMap;
import java.util.Map;

import lushfile.core.guice.ServletScoped;

@ServletScoped
public class LushControllerMetadata {
	Map<String, LushController> packageMapping = new HashMap<String, LushController>();

	public Map<String, LushController> getPackageMapping() {
		return packageMapping;
	}

}
