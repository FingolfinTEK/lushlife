package stlog.decorator;

import java.util.concurrent.atomic.AtomicBoolean;

import stlog.Level;
import stlog.spi.LogProvider;
import stlog.spi.LogProviderDecorator;
import stlog.util.LogLog;

public class ChangeLoglevel implements LogProviderDecorator {
	private String enumClass;
	private String value;
	private Level to;

	public void setValue(String value) {
		this.value = value;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

	public void setTo(String to) {
		this.to = Level.valueOf(to);
	}

	private final AtomicBoolean reportError = new AtomicBoolean(true);

	public LogProvider decorate(final LogProvider binder) {
		if (enumClass == null || value == null || to == null) {
			if (reportError.getAndSet(false)) {
				LogLog.reportFailure(
						"IllealState: Ignore ChangeLogLevel logId[ " + enumClass
								+ "#" + value + "] to[" + to + "]", null);
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

			private boolean isTarget(Enum<?> logId) {
				if (!logId.getDeclaringClass().getName().equals(enumClass)) {
					return false;
				}
				if (!logId.name().equals(value)) {
					return false;
				}
				return true;
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				if (isTarget(logId)) {
					binder.log(to, logId, message, e);
				} else {
					binder.log(level, logId, message, e);
				}
			}
		};
	}

}
