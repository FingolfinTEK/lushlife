package org.slf4j.i18n;

import org.slf4j.Logger;
import org.slf4j.spi.LocationAwareLogger;

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
		public void log(Logger logger, String format, Object[] argArray) {
			logger.trace(format, argArray);
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			logger.trace(format, t);
		}

		@Override
		public int intValue() {
			return LocationAwareLogger.TRACE_INT;
		}
	},

	DEBUG {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isDebugEnabled();
		}

		@Override
		public void log(Logger logger, String format, Object[] argArray) {
			logger.debug(format, argArray);
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			logger.debug(format, t);
		}

		@Override
		public int intValue() {
			return LocationAwareLogger.DEBUG_INT;
		}
	},

	INFO {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isInfoEnabled();
		}

		@Override
		public void log(Logger logger, String format, Object[] argArray) {
			logger.info(format, argArray);
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			logger.info(format, t);
		}

		@Override
		public int intValue() {
			return LocationAwareLogger.INFO_INT;
		}
	},

	WARN {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isWarnEnabled();
		}

		@Override
		public void log(Logger logger, String format, Object[] argArray) {
			logger.warn(format, argArray);
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			logger.warn(format, t);
		}

		@Override
		public int intValue() {
			return LocationAwareLogger.WARN_INT;
		}
	},

	ERROR {
		@Override
		public boolean isEnabledFor(Logger logger) {
			return logger.isErrorEnabled();
		}

		@Override
		public void log(Logger logger, String format, Object[] argArray) {
			logger.error(format, argArray);
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			logger.error(format, t);
		}

		@Override
		public int intValue() {
			return LocationAwareLogger.ERROR_INT;
		}
	},

	/**
	 * log level is not specified.
	 */
	NOT_SPECIFIED {
		@Override
		public boolean isEnabledFor(Logger logger) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void log(Logger logger, String format, Object[] argArray) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int intValue() {
			return Integer.MIN_VALUE;
		}
	};

	/**
	 * Is the logger instance enabled for this level?
	 */
	public abstract boolean isEnabledFor(Logger logger);

	/**
	 * Write log through slf4j logger.
	 */
	public abstract void log(Logger logger, String format, Object[] argArray);

	/**
	 * Write log through slf4j logger.
	 */
	public abstract void log(Logger logger, String format, Throwable t);

	/**
	 * Get log level integer value.
	 * 
	 * @return
	 */
	public abstract int intValue();

}
