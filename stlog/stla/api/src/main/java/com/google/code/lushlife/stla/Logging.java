package com.google.code.lushlife.stla;

import java.net.URL;
import java.util.Locale;

import com.google.code.lushlife.stla.configuration.DefaultLoggingManagerConfiguration;
import com.google.code.lushlife.stla.configuration.XMLLoggingManagerConfiguration;
import com.google.code.lushlife.stla.impl.LogImpl;
import com.google.code.lushlife.stla.impl.LoggingManagerImpl;
import com.google.code.lushlife.stla.slf4j.SLF4JLogProviderFactory;
import com.google.code.lushlife.stla.spi.LocaleSelector;
import com.google.code.lushlife.stla.spi.LogProvider;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;
import com.google.code.lushlife.stla.spi.LogProviderFactory;
import com.google.code.lushlife.stla.spi.LoggingManager;


public class Logging {

	static private LoggingManager loggingManager;
	static private LogProviderFactory loggingFactory;

	static {
		initializeLoggingFactory();
		initializeLoggingManager();
	}

	private static void initializeLoggingFactory() {
		loggingFactory = new SLF4JLogProviderFactory();
	}

	private static void initializeLoggingManager() {

		loggingManager = xmlConfiguration();
		// default logging manager
		if (loggingManager == null) {
			loggingManager = new LoggingManagerImpl(
					new DefaultLoggingManagerConfiguration());
		}

		loggingManager.initialize();
	}

	private static LoggingManager xmlConfiguration() {
		String xmlFileName = System.getProperty("stla.configuration");
		if (xmlFileName == null) {
			xmlFileName = "stla.xml";
		}
		URL xmlResource = Thread.currentThread().getContextClassLoader()
				.getResource(xmlFileName);
		if (xmlResource != null) {
			return new LoggingManagerImpl(new XMLLoggingManagerConfiguration(
					xmlResource));
		}
		return null;
	}

	static public <E extends Enum<E>> Level getLevel(E logId) {
		return loggingManager.getLevelResolver().toLevel(logId);
	}

	static public <E extends Enum<E>> String getMessage(E logId) {
		return loggingManager.getMessageResolver()
				.toMessage(logId, getLocale());
	}

	static public <E extends Enum<E>> String getMessage(E logId, Locale locale) {
		return loggingManager.getMessageResolver().toMessage(logId, locale);
	}

	static public Locale getLocale() {
		return loggingManager.getLocaleSelector().getLocale();
	}

	static public Locale setLoacle(Locale locale) {
		LocaleSelector selector = loggingManager.getLocaleSelector();
		Locale oldLocale = selector.getLocale();
		selector.setLocale(locale);
		return oldLocale;
	}

	static public Log getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static Log getLogger(String name) {
		LogProvider binder = loggingFactory.getLogger(name);
		for (LogProviderDecorator decoretor : loggingManager.getDecorators()) {
			if (decoretor.isTarget(binder)) {
				binder = decoretor.decorate(binder);
			}
		}
		return new LogImpl(binder, loggingManager);
	}

}
