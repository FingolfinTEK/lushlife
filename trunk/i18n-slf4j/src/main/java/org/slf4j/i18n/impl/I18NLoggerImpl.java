package org.slf4j.i18n.impl;

import org.slf4j.Logger;
import org.slf4j.i18n.I18NLogger;
import org.slf4j.i18n.I18NLoggerManager;
import org.slf4j.i18n.LogLevel;

public class I18NLoggerImpl implements I18NLogger {
	private Logger logger;
	private I18NLoggerManager manager;
	private LogLevel defaultLogLevel;
	private String defaultMessage;

	public I18NLoggerImpl(Logger logger, I18NLoggerManager manager,
			LogLevel defaultLogLevel, String defaultMessage) {
		this.logger = logger;
		this.manager = manager;
		this.defaultLogLevel = defaultLogLevel;
		this.defaultMessage = defaultMessage;
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
		writeLog(defaultLogLevel, logId, argArray);
	}

	public <E extends Enum<E>> void log(E logId, Throwable t) {
		writeLog(defaultLogLevel, logId, t);
	}

	private <E extends Enum<E>> void writeLog(LogLevel defaultLogLevel,
			E logId, Throwable t) {
		LogLevel level = manager.resolveLogLevel(logId, defaultLogLevel);
		String format = manager.resolveLogMessage(logId, defaultMessage);
		level.log(logger, format, t);
	}

	private <E extends Enum<E>> void writeLog(LogLevel defaultLogLevel,
			E logId, Object[] argArray) {
		LogLevel level = manager.resolveLogLevel(logId, defaultLogLevel);
		String format = manager.resolveLogMessage(logId, defaultMessage);
		level.log(logger, format, argArray);
	}

	public <E extends Enum<E>> void debug(E logId, Object... argArray) {
		writeLog(LogLevel.DEBUG, logId, argArray);
	}

	public <E extends Enum<E>> void debug(E logId, Throwable t) {
		writeLog(LogLevel.DEBUG, logId, t);
	}

	public <E extends Enum<E>> void error(E logId, Object... argArray) {
		writeLog(LogLevel.ERROR, logId, argArray);
	}

	public <E extends Enum<E>> void error(E logId, Throwable t) {
		writeLog(LogLevel.ERROR, logId, t);
	}

	public <E extends Enum<E>> void info(E logId, Object... argArray) {
		writeLog(LogLevel.INFO, logId, argArray);
	}

	public <E extends Enum<E>> void info(E logId, Throwable t) {
		writeLog(LogLevel.INFO, logId, t);
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
		writeLog(LogLevel.TRACE, logId, argArray);
	}

	public <E extends Enum<E>> void trace(E logId, Throwable t) {
		writeLog(LogLevel.TRACE, logId, t);
	}

	public <E extends Enum<E>> void warn(E logId, Object... argArray) {
		writeLog(LogLevel.WARN, logId, argArray);
	}

	public <E extends Enum<E>> void warn(E logId, Throwable t) {
		writeLog(LogLevel.WARN, logId, t);
	}

}
