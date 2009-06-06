package lushfile.plugins.resources;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultResourceManager.class)
public interface ResourceManager {

	WriteTo write(String controllerName, String type, String resource);

	String createUrl(String controllerName, String type, String resource);
}
