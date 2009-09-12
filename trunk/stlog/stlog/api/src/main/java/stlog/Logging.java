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
import stlog.util.LogLog;
import stlog.util.ServiceLoader;

public class Logging {

	static private LoggingManager loggingManager;
	static private LogProviderFactory loggingFactory;

	static {
		initializeLoggingFactory();
		initializeLoggingManager();
	}

	private static void initializeLoggingFactory() {
		ServiceLoader<LogProviderFactory> services = ServiceLoader.load(
				LogProviderFactory.class, Thread.currentThread()
						.getContextClassLoader());
		for (LogProviderFactory factory : services) {
			if (Logging.loggingFactory == null) {
				Logging.loggingFactory = factory;
			} else {
				if (!loggingFactory.getClass().equals(factory.getClass())) {
					LogLog.reportFailure("already loaded LoggingFactory ["
							+ Logging.loggingFactory.getClass() + "]. ignore ["
							+ factory.getClass() + "]", null);
				}
			}
		}

		if (Logging.loggingFactory == null) {
			Logging.loggingFactory = new SLF4JLogProviderFactory();
		}
		loggingFactory.initialize();
	}

	private static void initializeLoggingManager() {
		String xmlFileName = System.getProperty("stlog.configuration");
		if (xmlFileName == null) {
			xmlFileName = "stlog.xml";
		}
		URL xmlResource = Thread.currentThread().getContextClassLoader()
				.getResource(xmlFileName);
		if (xmlResource != null) {
			Logging.loggingManager = new LoggingManagerImpl(
					new XMLLoggingManagerConfiguration(xmlResource));
		}
		// default logging manager
		if (Logging.loggingManager == null) {
			Logging.loggingManager = new LoggingManagerImpl(
					new DefaultLoggingManagerConfiguration());
		}

		loggingManager.initialize();
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
