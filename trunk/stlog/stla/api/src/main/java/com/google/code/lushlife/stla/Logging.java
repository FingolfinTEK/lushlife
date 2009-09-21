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
