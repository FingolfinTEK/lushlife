package org.slf4j.i18n;

import org.slf4j.Logger;

/**
 * The I18NLogger interface is the main user entry point of i18n-slf4j API.
 * 
 * @author Takeshi Kondo;
 */
public interface I18NLogger {

	/**
	 * Is the logger instance enabled for the level associated with log id?
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * info.
	 */
	<E extends Enum<E>> boolean isEnabledFor(E logId);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * info.
	 */
	<E extends Enum<E>> void log(E logId, Object... argArray);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * info.
	 */
	<E extends Enum<E>> void log(E logId, Throwable t);

	/**
	 * Is the logger instance enabled for the level associated with log id?
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * error.
	 */
	<E extends Enum<E>> boolean isErrorEnabled(E logId);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * error.
	 */
	<E extends Enum<E>> void error(E logId, Object... argArray);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * error.
	 */
	<E extends Enum<E>> void error(E logId, Throwable t);

	/**
	 * Is the logger instance enabled for the level associated with log id? If
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * warn.
	 */
	<E extends Enum<E>> boolean isWarnEnabled(E logId);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * warn.
	 */
	<E extends Enum<E>> void warn(E logId, Object... argArray);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * warn.
	 */
	<E extends Enum<E>> void warn(E logId, Throwable t);

	/**
	 * Is the logger instance enabled for the level associated with log id?
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * info.
	 */
	<E extends Enum<E>> boolean isInfoEnabled(E logId);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * info.
	 */
	<E extends Enum<E>> void info(E logId, Object... argArray);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * info.
	 */
	<E extends Enum<E>> void info(E logId, Throwable t);

	/**
	 * Is the logger instance enabled for the level associated with log id?
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * debug.
	 */
	<E extends Enum<E>> boolean isDebugEnabled(E logId);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * debug.
	 */
	<E extends Enum<E>> void debug(E logId, Object... argArray);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * debug.
	 */
	<E extends Enum<E>> void debug(E logId, Throwable t);

	/**
	 * Is the logger instance enabled for the level associated with log id?
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * trace.
	 */
	<E extends Enum<E>> boolean isTraceEnabled(E logId);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * trace.
	 */
	<E extends Enum<E>> void trace(E logId, Object... argArray);

	/**
	 * Log a message at the level associated with log id.
	 * 
	 * If log id isn't associated with log level, the log level is regarded as
	 * trace.
	 */
	<E extends Enum<E>> void trace(E logId, Throwable t);

	/**
	 * Return delegating slf4j logger
	 */
	public Logger getDelegate();
}
