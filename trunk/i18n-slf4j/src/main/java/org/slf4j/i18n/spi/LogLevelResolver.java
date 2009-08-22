package org.slf4j.i18n.spi;

import org.slf4j.i18n.LogLevel;

/**
 *The LogLevelResolver resolve log level from log id.
 * 
 * @author Takeshi Kondo
 * 
 */
public interface LogLevelResolver {

	<E extends Enum<E>> LogLevel toLogLevel(E logId);

}
