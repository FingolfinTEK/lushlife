package stlog;

import java.util.Locale;

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
		ServiceLoader<LoggingManager> services = ServiceLoader.load(
				LoggingManager.class, Thread.currentThread()
						.getContextClassLoader());
		for (LoggingManager manager : services) {
			if (loggingManager == null) {
				Logging.loggingManager = manager;
			} else {
				if (!loggingManager.getClass().equals(manager.getClass())) {
					LogLog.reportFailure("already loaded LogManager ["
							+ loggingManager.getClass() + "]. ignore ["
							+ manager.getClass() + "]", null);
				}
			}
		}
		// default logging manager
		if (Logging.loggingManager == null) {
			Logging.loggingManager = new LoggingManagerImpl();
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
