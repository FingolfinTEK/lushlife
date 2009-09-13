package stla.decorator;

import stla.Level;
import stla.spi.LogProvider;

public class Block extends LogIdHandlingDecoratorBase {

	public LogProvider decorate(final LogProvider logProvider) {

		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				if (isTarget(logId)) {
					return;
				}
				logProvider.log(level, logId, format, params);
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				if (isTarget(logId)) {
					return;
				}
				logProvider.log(level, logId, message, e);

			}

			public boolean isEnableFor(Level level, Enum<?> logId) {
				if (isTarget(logId)) {
					return false;
				}
				return logProvider.isEnableFor(level, logId);
			}

			public String getName() {
				return logProvider.getName();
			}
		};
	}

}
