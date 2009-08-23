package org.slf4j.i18n;

import org.slf4j.i18n.spi.LogLevelResolver;
import org.slf4j.i18n.spi.LogMessageFormatResolver;

public class I18NLoggerManager {

	public I18NLoggerManager(LogLevelResolver loglevelResolver,
			LogMessageFormatResolver logMessaggeResolver) {
		this.loglevelResolver = loglevelResolver;
		this.logMessaggeResolver = logMessaggeResolver;
	}

	private LogLevelResolver loglevelResolver;

	private LogMessageFormatResolver logMessaggeResolver;

	public <E extends Enum<E>> LogLevel resolveLogLevel(E logId,
			LogLevel defaultLogLevel) {
		LogLevel level = loglevelResolver.toLogLevel(logId);
		if (level == null || level.equals(LogLevel.NOT_SPECIFIED)) {
			return defaultLogLevel;
		}
		return level;
	}

	public <E extends Enum<E>> String resolveLogMessage(E logId,
			String defaultMessage) {
		String message = logMessaggeResolver.toMessageFormat(logId);
		if (message == null) {
			return defaultMessage;
		}
		return message;
	}

}
