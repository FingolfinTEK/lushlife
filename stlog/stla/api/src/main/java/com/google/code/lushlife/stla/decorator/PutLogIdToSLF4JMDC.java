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
package com.google.code.lushlife.stla.decorator;

import org.slf4j.MDC;

import com.google.code.lushlife.stla.Level;
import com.google.code.lushlife.stla.spi.LogProvider;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;

/**
 * @author Takeshi Kondo
 */
public class PutLogIdToSLF4JMDC extends LogProviderDecoratorBase implements
		LogProviderDecorator {

	private String key = "logId";

	public void setKey(String key) {
		this.key = key;
	}

	public LogProvider decorate(final LogProvider proceed) {
		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				try {
					MDC.put(key, logId.name());
					proceed.log(level, logId, format, params);
				} finally {
					MDC.remove(key);
				}
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				try {
					MDC.put(key, logId.name());
					proceed.log(level, logId, message, e);
				} finally {
					MDC.remove(key);
				}
			}

			public boolean isEnableFor(Level level, Enum<?> logId) {
				return proceed.isEnableFor(level, logId);
			}

			public String getName() {
				return proceed.getName();
			}
		};
	}

}
