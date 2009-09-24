package org.lushlife.stla.provider;

import java.text.MessageFormat;
import java.util.logging.Logger;

import org.lushlife.stla.Level;
import org.lushlife.stla.spi.LogProvider;

public class JDKLogProvider implements LogProvider {
	Logger logger;

	public JDKLogProvider(Logger logger) {
		this.logger = logger;
	}

	public String getName() {
		return logger.getName();
	}

	public boolean isEnableFor(Level level, Enum<?> logId) {
		switch (level) {
		case ERROR:
			return logger.isLoggable(java.util.logging.Level.SEVERE);
		case WARN:
			return logger.isLoggable(java.util.logging.Level.WARNING);
		case INFO:
			return logger.isLoggable(java.util.logging.Level.INFO);
		case DEBUG:
			return logger.isLoggable(java.util.logging.Level.FINE);
		case TRACE:
			return logger.isLoggable(java.util.logging.Level.FINER);
		}
		throw new IllegalArgumentException("Unreachable code");
	}

	public void log(Level level, Enum<?> logId, String message, Throwable e,
			Object[] params) {
		if (!isEnableFor(level, logId)) {
			return;
		}
		try {
			message = MessageFormat.format(message, params);
		} catch (IllegalArgumentException ex) {
			logger.log(java.util.logging.Level.FINE,
					"illegal argument eception ", ex);
		}

		switch (level) {
		case ERROR:
			logger.log(java.util.logging.Level.SEVERE, message, e);
		case WARN:
			logger.log(java.util.logging.Level.WARNING, message, e);
		case INFO:
			logger.log(java.util.logging.Level.INFO, message, e);
		case DEBUG:
			logger.log(java.util.logging.Level.FINE, message, e);
		case TRACE:
			logger.log(java.util.logging.Level.FINER, message, e);
		}
		throw new IllegalArgumentException("Unreachable code");

	}

}
