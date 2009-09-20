package com.google.code.lushlife.stla.spi;

import java.util.Locale;

public interface MessageResolver {
	<E extends Enum<E>> String toMessage(E logId, Locale locale);
}
