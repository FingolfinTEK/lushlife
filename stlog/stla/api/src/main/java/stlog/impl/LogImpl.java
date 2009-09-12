package stlog.impl;

import java.util.Locale;

import stlog.Level;
import stlog.Log;
import stlog.spi.LogProvider;
import stlog.spi.LoggingManager;

public class LogImpl implements Log {
	private LogProvider logger;
	private LoggingManager manager;

	public LogImpl(LogProvider logger, LoggingManager manager) {
		this.logger = logger;
		this.manager = manager;
	}

	public <E extends Enum<E>> boolean isEnableFor(E logId) {
		Level level = manager.getLevelResolver().toLevel(logId);
		return logger.isEnableFor(level, logId);
	}

	public <E extends Enum<E>> void log(E logId, Object... params) {
		Level level = manager.getLevelResolver().toLevel(logId);
		Locale locale = manager.getLocaleSelector().getLocale();
		String message = manager.getMessageResolver().toMessage(logId, locale);
		logger.log(level, logId, message, params);
	}

	public <E extends Enum<E>> void log(E logId, Throwable e) {
		Level level = manager.getLevelResolver().toLevel(logId);
		Locale locale = manager.getLocaleSelector().getLocale();
		String message = manager.getMessageResolver().toMessage(logId, locale);
		logger.log(level, logId, message, e);
	}

}
