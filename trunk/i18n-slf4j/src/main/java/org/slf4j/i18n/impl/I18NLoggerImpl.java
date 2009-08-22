package org.slf4j.i18n.impl;

import org.slf4j.Logger;
import org.slf4j.i18n.I18NLogger;
import org.slf4j.i18n.I18NLoggerManager;
import org.slf4j.i18n.LogLevel;

public class I18NLoggerImpl implements I18NLogger {
	private Logger logger;
	private I18NLoggerManager manager;
	private LogLevel defaultLogLevel;

	public I18NLoggerImpl(Logger logger, I18NLoggerManager manager,
			LogLevel defaultLogLevel) {
		this.logger = logger;
		this.manager = manager;
		this.defaultLogLevel = defaultLogLevel;
	}

	public Logger getDelegate() {
		return logger;
	}

	public <E extends Enum<E>> boolean isEnabledFor(E logId) {
		return isEnabledFor(defaultLogLevel, logId);
	}

	private <E extends Enum<E>> boolean isEnabledFor(LogLevel defaultLogLevel,
			E logId) {
		LogLevel level = manager.resolveLogLevel(logId, defaultLogLevel);
		return level.isEnabledFor(logger);
	}

	public <E extends Enum<E>> void log(E logId, Object... argArray) {
		log(logId, null, argArray);
	}

	public <E extends Enum<E>> void log(E logId, Throwable t,
			Object... argArray) {
		log(defaultLogLevel, logId, t, argArray);
	}

	private <E extends Enum<E>> void log(LogLevel defaultLogLevel, E logId,
			Throwable t, Object... argArray) {
		LogLevel level = manager.resolveLogLevel(logId, defaultLogLevel);
		String format = manager.resolveLogMessage(logId);
		level.log(logger, format, t, argArray);
	}

	public <E extends Enum<E>> void debug(E logId, Object... argArray) {
		debug(logId, null, argArray);
	}

	public <E extends Enum<E>> void debug(E logId, Throwable t,
			Object... argArray) {
		log(LogLevel.DEBUG, logId, t, argArray);
	}

	public <E extends Enum<E>> void error(E logId, Object... argArray) {
		error(logId, null, argArray);
	}

	public <E extends Enum<E>> void error(E logId, Throwable t,
			Object... argArray) {
		log(LogLevel.ERROR, logId, t, argArray);
	}

	public <E extends Enum<E>> void info(E logId, Object... argArray) {
		info(logId, null, argArray);
	}

	public <E extends Enum<E>> void info(E logId, Throwable t,
			Object... argArray) {
		log(LogLevel.INFO, logId, t, argArray);
	}

	public <E extends Enum<E>> boolean isDebugEnabled(E logId) {
		return isEnabledFor(LogLevel.DEBUG, logId);
	}

	public <E extends Enum<E>> boolean isErrorEnabled(E logId) {
		return isEnabledFor(LogLevel.ERROR, logId);
	}

	public <E extends Enum<E>> boolean isInfoEnabled(E logId) {
		return isEnabledFor(LogLevel.INFO, logId);
	}

	public <E extends Enum<E>> boolean isTraceEnabled(E logId) {
		return isEnabledFor(LogLevel.TRACE, logId);
	}

	public <E extends Enum<E>> boolean isWarnEnabled(E logId) {
		return isEnabledFor(LogLevel.WARN, logId);
	}

	public <E extends Enum<E>> void trace(E logId, Object... argArray) {
		trace(logId, null, argArray);
	}

	public <E extends Enum<E>> void trace(E logId, Throwable t,
			Object... argArray) {
		log(LogLevel.TRACE, logId, t, argArray);

	}

	public <E extends Enum<E>> void warn(E logId, Object... argArray) {
		warn(logId, null, argArray);
	}

	public <E extends Enum<E>> void warn(E logId, Throwable t,
			Object... argArray) {
		log(LogLevel.WARN, logId, t, argArray);
	}
}
