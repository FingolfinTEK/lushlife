package stlog.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stlog.spi.LogProvider;
import stlog.spi.LogProviderFactory;

public class SLF4JLogProviderFactory implements LogProviderFactory {

	public LogProvider getLogger(String category) {
		Logger logger = LoggerFactory.getLogger(category);
		return new SLF4JLogProvider(logger);
	}

	public void initialize() {
		// do nothing;
	}

}
