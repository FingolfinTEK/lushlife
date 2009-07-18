package glassbottle.plugins.groovy.impl;

import java.util.HashMap;
import java.util.Map;

public class TemplateHandler {
	String tempalte;
	Map<String, String> map = new HashMap<String, String>();
	private Map<String, String> params;

	public TemplateHandler init(String template) {
		this.tempalte = template;
		return this;
	}

	public void put(String name, String value) {
		map.put(name, value);
	}

	public String get(String name) {
		return map.get(name);
	}

	public String param(String name) {
		return params.get(name);
	}

	public TemplateHandler init(String resourceName, Map<String, String> params) {
		this.tempalte = resourceName;
		this.params = params;
		return this;
	}

}
