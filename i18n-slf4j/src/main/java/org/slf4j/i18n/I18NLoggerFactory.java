package org.slf4j.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.i18n.impl.AnnotationMessageFormatResolver;
import org.slf4j.i18n.impl.AnnotationLogLevelResolver;
import org.slf4j.i18n.impl.CompositeMessageFormatResolver;
import org.slf4j.i18n.impl.CompositeLogLevelResolver;
import org.slf4j.i18n.impl.I18NLoggerImpl;
import org.slf4j.i18n.impl.ResourceBundleMessageFormatResolver;
import org.slf4j.i18n.impl.ResourceBundleLogLevelResolver;

/**
 * The I18NLoggerFactory is a utility class producing I18NLoggers.
 * 
 * @author Takeshi Kondo
 * 
 */
public class I18NLoggerFactory {
	static private I18NLoggerFactory INSTANCE;

	public static I18NLoggerFactory getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(I18NLoggerFactory factory) {
		INSTANCE = factory;
	}

	static {
		CompositeLogLevelResolver logLevelResolver = new CompositeLogLevelResolver();
		logLevelResolver.add(new ResourceBundleLogLevelResolver());
		logLevelResolver.add(new AnnotationLogLevelResolver());

		CompositeMessageFormatResolver formatResolver = new CompositeMessageFormatResolver();
		formatResolver.add(new ResourceBundleMessageFormatResolver());
		formatResolver.add(new AnnotationMessageFormatResolver());

		I18NLoggerManager manager = new I18NLoggerManager(logLevelResolver,
				formatResolver);
		INSTANCE = new I18NLoggerFactory(manager);
	}

	static public I18NLogger getLogger(String name) {
		return INSTANCE.createLogger(name);
	}

	static public I18NLogger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	private final I18NLoggerManager manager;
	private LogLevel defaltLogLevel = LogLevel.INFO;

	public I18NLoggerFactory(I18NLoggerManager manager) {
		this.manager = manager;
	}

	public I18NLogger createLogger(String name) {
		Logger logger = LoggerFactory.getLogger(name);
		return new I18NLoggerImpl(logger, manager, defaltLogLevel);
	}
}
