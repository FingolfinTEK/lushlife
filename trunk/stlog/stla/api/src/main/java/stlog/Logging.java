package stlog;

import java.net.URL;
import java.util.Locale;

import stlog.configuration.DefaultLoggingManagerConfiguration;
import stlog.configuration.XMLLoggingManagerConfiguration;
import stlog.impl.LogImpl;
import stlog.impl.LoggingManagerImpl;
import stlog.slf4j.SLF4JLogProviderFactory;
import stlog.spi.LocaleSelector;
import stlog.spi.LogProvider;
import stlog.spi.LogProviderDecorator;
import stlog.spi.LogProviderFactory;
import stlog.spi.LoggingManager;

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
		String xmlFileName = System.getProperty("stlog.configuration");
		if (xmlFileName == null) {
			xmlFileName = "stlog.xml";
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
			binder = decoretor.decorate(binder);
		}
		return new LogImpl(binder, loggingManager);
	}

}
