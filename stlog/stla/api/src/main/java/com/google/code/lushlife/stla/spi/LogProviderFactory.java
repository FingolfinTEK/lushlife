package com.google.code.lushlife.stla.spi;

public interface LogProviderFactory {
	LogProvider getLogger(String category);

}
