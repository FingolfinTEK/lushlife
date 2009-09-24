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
package org.lushlife.stla.decorator;

import org.lushlife.stla.Level;
import org.lushlife.stla.spi.LogProvider;
import org.lushlife.stla.spi.LogProviderDecorator;

/**
 * @author Takeshi Kondo
 */
public class AddLogIdToMessage extends LogProviderDecoratorBase implements
		LogProviderDecorator {

	public LogProvider decorate(final LogProvider proceed) {
		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				proceed.log(level, logId, "<" + logId.name() + "> " + format,
						params);
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e, Object[] params) {
				proceed.log(level, logId, "<" + logId.name() + "> " + message,
						e, params);
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
