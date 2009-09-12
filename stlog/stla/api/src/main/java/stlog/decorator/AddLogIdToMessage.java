package stlog.decorator;

import stlog.Level;
import stlog.spi.LogProvider;
import stlog.spi.LogProviderDecorator;

public class AddLogIdToMessage implements LogProviderDecorator {

	public LogProvider decorate(final LogProvider proceed) {
		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				proceed.log(level, logId, "<" + logId.name() + "> " + format,
						params);
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				proceed.log(level, logId, "<" + logId.name() + "> " + message,
						e);
			}

			public boolean isEnableFor(Level level, Enum<?> logId) {
				return proceed.isEnableFor(level, logId);
			}
		};
	}
}
