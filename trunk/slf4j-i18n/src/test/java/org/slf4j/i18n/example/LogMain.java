package org.slf4j.i18n.example;

import org.slf4j.i18n.I18NLogger;
import org.slf4j.i18n.I18NLoggerFactory;

public class LogMain {

	public static void main(String[] args) {
		I18NLogger logger = I18NLoggerFactory.getLogger(LogMain.class);

		logger.log(LogMessages.TEST0001, "xxxx");
		logger.log(LogMessages.TEST0001, new NullPointerException());

		logger.error(LogMessages.TEST0002, "xxxx");
		logger.warn(LogMessages.TEST0002, new NullPointerException());

	}

}
