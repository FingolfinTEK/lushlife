/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.lushlife.stla;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;

import org.lushlife.stla.configuration.DefaultLoggingManagerConfiguration;
import org.lushlife.stla.configuration.XMLLoggingManagerConfiguration;
import org.lushlife.stla.impl.LogImpl;
import org.lushlife.stla.impl.LoggingManagerImpl;
import org.lushlife.stla.provider.JDKLogProviderFactory;
import org.lushlife.stla.provider.SLF4JLogProviderFactory;
import org.lushlife.stla.spi.LocaleSelector;
import org.lushlife.stla.spi.LogProvider;
import org.lushlife.stla.spi.LogProviderDecorator;
import org.lushlife.stla.spi.LogProviderFactory;
import org.lushlife.stla.spi.LoggingManager;

/**
 * @author Takeshi Kondo
 */
public class Logging {

	static private LoggingManager loggingManager;
	static private LogProviderFactory loggingFactory;

	static {
		initializeLoggingFactory();
		initializeLoggingManager();
	}

	private static void initializeLoggingFactory() {
		try {
			Class.forName("org.slf4j.Logger");
			try {
				Class.forName("org.slf4j.impl.JDK14LoggerFactory");
				loggingFactory = new JDKLogProviderFactory();
			} catch (ClassNotFoundException e) {
				loggingFactory = new SLF4JLogProviderFactory();
			}
		} catch (ClassNotFoundException e) {
			loggingFactory = new JDKLogProviderFactory();
		}
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

	static public <E extends Enum<E>> String getMessage(E logId,
			Object... params) {
		return getMessageFromIdAndLocale(logId, getLocale(), params);
	}

	static public <E extends Enum<E>> String getMessageFromIdAndLocale(E logId,
			Locale locale, Object... params) {
		String message = loggingManager.getMessageResolver().toMessage(logId,
				locale);
		if (params.length == 0) {
			return message;
		}
		return MessageFormat.format(message, params);
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

	static public Log getLog(Class<?> clazz) {
		return getLog(clazz.getName());
	}

	public static Log getLog(String name) {
		LogProvider binder = loggingFactory.getLogger(name);
		for (LogProviderDecorator decoretor : loggingManager.getDecorators()) {
			if (decoretor.isTarget(binder)) {
				binder = decoretor.decorate(binder);
			}
		}
		return new LogImpl(binder, loggingManager);
	}

}
