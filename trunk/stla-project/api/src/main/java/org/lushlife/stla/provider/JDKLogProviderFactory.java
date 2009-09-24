package org.lushlife.stla.provider;

import java.util.logging.LogManager;

import org.lushlife.stla.spi.LogProvider;
import org.lushlife.stla.spi.LogProviderFactory;

public class JDKLogProviderFactory implements LogProviderFactory {

	public LogProvider getLogger(String category) {
		return new JDKLogProvider(LogManager.getLogManager()
				.getLogger(category));
	}
}
