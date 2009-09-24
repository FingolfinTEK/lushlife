package org.lushlife.stla.provider;

import java.util.logging.Logger;

import org.lushlife.stla.spi.LogProvider;
import org.lushlife.stla.spi.LogProviderFactory;

public class JDKLogProviderFactory implements LogProviderFactory {

	public LogProvider getLogger(String category) {
		return new JDKLogProvider(Logger.getLogger(category));
	}
}
