package org.lushlife.inject;

import java.util.ServiceLoader;

import org.lushlife.inject.dsl.Configutaion;
import org.lushlife.inject.dsl.Module;

public class ServiceLoaderModule implements Module {
	ClassLoader loader;

	public ServiceLoaderModule(ClassLoader loader) {
		this.loader = loader;
	}

	public void configutaion(Configutaion configutaion) {
		for (Module m : ServiceLoader.load(Module.class, loader)) {
			configutaion.include(m);
		}
	}
}
