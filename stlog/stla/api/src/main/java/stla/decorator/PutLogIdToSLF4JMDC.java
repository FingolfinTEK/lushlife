package stla.decorator;

import org.slf4j.MDC;

import stla.Level;
import stla.spi.LogProvider;
import stla.spi.LogProviderDecorator;

public class PutLogIdToSLF4JMDC extends LogProviderDecoratorBase implements
		LogProviderDecorator {

	private String key = "logId";

	public void setKey(String key) {
		this.key = key;
	}

	public LogProvider decorate(final LogProvider proceed) {
		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				try {
					MDC.put(key, logId.name());
					proceed.log(level, logId, format, params);
				} finally {
					MDC.remove(key);
				}
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				try {
					MDC.put(key, logId.name());
					proceed.log(level, logId, message, e);
				} finally {
					MDC.remove(key);
				}
			}

			public boolean isEnableFor(Level level, Enum<?> logId) {
				return proceed.isEnableFor(level, logId);
			}

			public String getName() {
				return proceed.getName();
			}
		};
	}

}
