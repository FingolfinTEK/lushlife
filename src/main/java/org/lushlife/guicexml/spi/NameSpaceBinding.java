package org.lushlife.guicexml.spi;

import java.util.Map;

public abstract class NameSpaceBinding {
	static public interface ToPackage {
		void toPackage(String name);

		void toPackage(Package pack);
	}

	Map<String, String> mapping;

	protected Map<String, String> mapping() {
		return mapping;
	}

	public void configure(Map<String, String> mapping) {
		this.mapping = mapping;
		configure();
	}

	protected ToPackage namespace(final String namespace) {
		return new ToPackage() {

			@Override
			public void toPackage(String name) {
				mapping.put(namespace, name);
			}

			@Override
			public void toPackage(Package pack) {
				toPackage(pack.getName());
			}
		};
	}

	protected abstract void configure();

}
