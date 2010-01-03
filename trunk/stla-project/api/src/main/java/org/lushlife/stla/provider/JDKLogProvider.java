package org.lushlife.stla.provider;

import java.text.MessageFormat;
import java.util.logging.LogRecord;
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
			log(SELF, java.util.logging.Level.SEVERE, message, e);
			return;
		case WARN:
			log(SELF, java.util.logging.Level.WARNING, message, e);
			return;
		case INFO:
			log(SELF, java.util.logging.Level.INFO, message, e);
			return;
		case DEBUG:
			log(SELF, java.util.logging.Level.FINE, message, e);
			return;
		case TRACE:
			log(SELF, java.util.logging.Level.FINER, message, e);
			return;
		}
		throw new IllegalArgumentException("Unreachable code " + level);

	}

	private void log(String callerFQCN, java.util.logging.Level level,
			String msg, Throwable t) {
		LogRecord record = new LogRecord(level, msg);
		record.setLoggerName(getName());
		record.setThrown(t);
		fillCallerData(callerFQCN, record);
		logger.log(record);

	}

	static String SELF = JDKLogProvider.class.getName();
	static String SUPER = LogProvider.class.getName();

	final private void fillCallerData(String callerFQCN, LogRecord record) {
		StackTraceElement[] steArray = new Throwable().getStackTrace();

		int selfIndex = -1;
		for (int i = 0; i < steArray.length; i++) {
			final String className = steArray[i].getClassName();
			if (className.equals(callerFQCN) || className.equals(SUPER)) {
				selfIndex = i;
				break;
			}
		}

		int found = -1;
		for (int i = selfIndex + 1; i < steArray.length; i++) {
			final String className = steArray[i].getClassName();
			if (!className.startsWith("org.lushlife.stla")) {
				found = i;
				break;
			}
		}

		if (found != -1) {
			StackTraceElement ste = steArray[found];
			// setting the class name has the side effect of setting
			// the needToInferCaller variable to false.
			record.setSourceClassName(ste.getClassName());
			record.setSourceMethodName(ste.getMethodName());
		}
	}
}
