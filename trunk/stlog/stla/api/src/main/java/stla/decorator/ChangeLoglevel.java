package stla.decorator;

import java.util.concurrent.atomic.AtomicBoolean;

import stla.Level;
import stla.spi.LogProvider;
import stla.spi.LogProviderDecorator;
import stla.util.LogLog;

public class ChangeLoglevel extends LogIdHandlingDecoratorBase implements
		LogProviderDecorator {
	private Level to;

	public void setTo(String to) {
		this.to = Level.valueOf(to.toUpperCase());
	}

	private final AtomicBoolean reportError = new AtomicBoolean(true);

	public LogProvider decorate(final LogProvider binder) {
		if (to == null) {
			if (reportError.getAndSet(false)) {
				LogLog.reportFailure("IllealState: to attribute is [" + to
						+ "]..", null);
			}
			return binder;
		}

		return new LogProvider() {
			public boolean isEnableFor(Level level, Enum<?> logId) {
				if (isTarget(logId)) {
					return binder.isEnableFor(to, logId);
				} else {
					return binder.isEnableFor(level, logId);
				}
			}

			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				if (isTarget(logId)) {
					binder.log(to, logId, format, params);
				} else {
					binder.log(level, logId, format, params);
				}
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				if (isTarget(logId)) {
					binder.log(to, logId, message, e);
				} else {
					binder.log(level, logId, message, e);
				}
			}

			public String getName() {
				return getName();
			}
		};
	}

}
