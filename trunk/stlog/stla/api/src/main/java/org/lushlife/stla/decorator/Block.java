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

/**
 * @author Takeshi Kondo
 */
public class Block extends LogIdHandlingDecoratorBase {

	public LogProvider decorate(final LogProvider logProvider) {

		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				if (isTarget(logId)) {
					return;
				}
				logProvider.log(level, logId, format, params);
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				if (isTarget(logId)) {
					return;
				}
				logProvider.log(level, logId, message, e);

			}

			public boolean isEnableFor(Level level, Enum<?> logId) {
				if (isTarget(logId)) {
					return false;
				}
				return logProvider.isEnableFor(level, logId);
			}

			public String getName() {
				return logProvider.getName();
			}
		};
	}

}
