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
		public void log(Logger logger, String format, Object[] argArray) {
			logger.trace(format, argArray);
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
			logger.trace(format, t);
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
		public void log(Logger logger, String format, Object[] argArray) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void log(Logger logger, String format, Throwable t) {
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
	public abstract void log(Logger logger, String format, Object[] argArray);

	/**
	 * delegate slf4j logger
	 */
	public abstract void log(Logger logger, String format, Throwable t);

}
