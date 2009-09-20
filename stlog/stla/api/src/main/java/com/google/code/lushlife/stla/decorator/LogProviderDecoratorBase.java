package com.google.code.lushlife.stla.decorator;

import com.google.code.lushlife.stla.spi.LogProvider;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;

public abstract class LogProviderDecoratorBase implements LogProviderDecorator {

	String category;

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isTarget(LogProvider provider) {
		if (category == null) {
			return true;
		}
		return provider.getName().startsWith(category);
	}

}
