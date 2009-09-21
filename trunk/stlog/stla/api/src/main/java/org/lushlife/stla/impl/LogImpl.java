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
package org.lushlife.stla.impl;

import java.util.Locale;

import org.lushlife.stla.Level;
import org.lushlife.stla.Log;
import org.lushlife.stla.spi.LogProvider;
import org.lushlife.stla.spi.LoggingManager;

/**
 * @author Takeshi Kondo
 */
public class LogImpl implements Log {
	private LogProvider logger;
	private LoggingManager manager;

	public LogImpl(LogProvider logger, LoggingManager manager) {
		this.logger = logger;
		this.manager = manager;
	}

	public <E extends Enum<E>> boolean isEnableFor(E logId) {
		Level level = manager.getLevelResolver().toLevel(logId);
		return logger.isEnableFor(level, logId);
	}

	public <E extends Enum<E>> void log(E logId, Object... params) {
		Level level = manager.getLevelResolver().toLevel(logId);
		Locale locale = manager.getLocaleSelector().getLocale();
		String message = manager.getMessageResolver().toMessage(logId, locale);
		logger.log(level, logId, message, params);
	}

	public <E extends Enum<E>> void log(E logId, Throwable e) {
		Level level = manager.getLevelResolver().toLevel(logId);
		Locale locale = manager.getLocaleSelector().getLocale();
		String message = manager.getMessageResolver().toMessage(logId, locale);
		logger.log(level, logId, message, e);
	}
}
