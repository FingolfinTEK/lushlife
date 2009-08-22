package org.slf4j.i18n;

import org.slf4j.Logger;

/**
 * This LogLevel represent slf4j log levels by enums.
 * 
 * @author Takeshi Kondo
 * 
 */
public enum LogLevel {
	TRACE {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isTraceEnabled();
		}

		@Override
		public void log(Logger logger, String format, Throwable t,
				Object... argArray) {
			logger.trace(format, argArray);
		}
	},

	DEBUG {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isDebugEnabled();
		}

		@Override
		public void log(Logger logger, String format, Throwable t,
				Object... argArray) {
			logger.debug(format, t, argArray);
		}
	},

	INFO {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isInfoEnabled();
		}

		@Override
		public void log(Logger logger, String format, Throwable t,
				Object... argArray) {
			logger.info(format, argArray);
		}
	},

	WARN {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isWarnEnabled();
		}

		@Override
		public void log(Logger logger, String format, Throwable t,
				Object... argArray) {
			logger.warn(format, t, argArray);
		}
	},

	ERROR {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isErrorEnabled();
		}

		@Override
		public void log(Logger logger, String format, Throwable t,
				Object... argArray) {
			logger.error(format, t, argArray);
		}
	},

	/**
	 * log level is not specified.
	 */
	NO_BINDING {
		@Override
		public boolean isEnabledFor(Logger logger) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void log(Logger logger, String format, Throwable t,
				Object... argArray) {
			throw new UnsupportedOperationException();
		}
	};
	/**
	 * delegate slf4j logger#isXXXEnabled
	 */
	public abstract boolean isEnabledFor(Logger logger);

	/**
	 * delegate slf4j logger
	 */
	public abstract void log(Logger logger, String format, Throwable t,
			Object... argArray);

}
