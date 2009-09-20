package com.google.code.lushlife.stla.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.lushlife.stla.spi.LogProvider;
import com.google.code.lushlife.stla.spi.LogProviderFactory;


public class SLF4JLogProviderFactory implements LogProviderFactory {

	public LogProvider getLogger(String category) {
		Logger logger = LoggerFactory.getLogger(category);
		return new SLF4JLogProvider(logger);
	}

}
