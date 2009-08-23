package org.slf4j.i18n.helpers;

import org.slf4j.i18n.I18NLoggerFactory;
import org.slf4j.i18n.LogLevel;

public class I18NLogs {
	static public <E extends Enum<E>> LogLevel getLevel(E logId) {
		return I18NLoggerFactory.getINSTANCE().getManager().resolveLogLevel(
				logId, null);
	}

	static public <E extends Enum<E>> String getMessage(E logId) {
		return I18NLoggerFactory.getINSTANCE().getManager().resolveLogMessage(
				logId, null);
	}
}
