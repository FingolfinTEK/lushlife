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
package org.lushlife.stla.provider;

import java.text.MessageFormat;

import org.lushlife.stla.Level;
import org.lushlife.stla.spi.LogProvider;
import org.slf4j.Logger;

/**
 * @author Takeshi Kondo
 */
public class SLF4JLogProvider implements LogProvider {
	private Logger logger;

	public SLF4JLogProvider(Logger logger) {
		this.logger = logger;
	}

	public boolean isEnableFor(Level level, Enum<?> logId) {
		switch (level) {
		case ERROR:
			return logger.isErrorEnabled();
		case WARN:
			return logger.isWarnEnabled();
		case INFO:
			return logger.isInfoEnabled();
		case DEBUG:
			return logger.isDebugEnabled();
		case TRACE:
			return logger.isTraceEnabled();
		}
		throw new IllegalArgumentException("Unreachable code");
	}

	public void log(Level level, Enum<?> logId, String message, Throwable e,
			Object[] params) {
		if (!isEnableFor(level, logId)) {
			return;
		}
		try {
			message = MessageFormat.format(message, params);
		} catch (IllegalArgumentException ex) {
			logger.debug("illegal argument eception ", ex);
		}
		switch (level) {
		case ERROR:
			logger.error(message, e);
			return;
		case WARN:
			logger.warn(message, e);
			return;
		case INFO:
			logger.info(message, e);
			return;
		case DEBUG:
			logger.debug(message, e);
			return;
		case TRACE:
			logger.trace(message, e);
			return;
		}
		throw new IllegalArgumentException("Unreachable code");
	}

	public String getName() {
		return logger.getName();
	}

}
