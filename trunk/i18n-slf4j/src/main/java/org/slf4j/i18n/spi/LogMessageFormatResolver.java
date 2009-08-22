package org.slf4j.i18n.spi;

/**
 * The LogMessageFormatResolver resolve log message format from log id.
 * 
 * @author Takeshi Kondo
 */
public interface LogMessageFormatResolver {

	<E extends Enum<E>> String toMessageFormat(E logId);

}
