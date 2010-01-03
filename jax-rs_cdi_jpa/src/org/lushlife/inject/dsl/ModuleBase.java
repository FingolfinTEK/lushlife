package org.lushlife.inject.dsl;

import java.net.URL;

public abstract class ModuleBase implements Module, Configutaion {
	Configutaion configutaion;

	public void beansXml(URL url) {
		configutaion.beansXml(url);
	}

	public void bean(Class<?> clazz) {
		configutaion.bean(clazz);
	}

	public void include(Module module) {
		configutaion.include(module);
	}

	public void configutaion(Configutaion configutaion) {
		this.configutaion = configutaion;
		configutaion();
	}

	abstract protected void configutaion();

}
