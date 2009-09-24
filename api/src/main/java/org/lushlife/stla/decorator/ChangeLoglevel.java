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

import java.util.concurrent.atomic.AtomicBoolean;

import org.lushlife.stla.Level;
import org.lushlife.stla.spi.LogProvider;
import org.lushlife.stla.spi.LogProviderDecorator;
import org.lushlife.stla.util.LogLog;

/**
 * @author Takeshi Kondo
 */
public class ChangeLoglevel extends LogIdHandlingDecoratorBase implements
		LogProviderDecorator {
	private Level to;

	public void setTo(String to) {
		this.to = Level.valueOf(to.toUpperCase());
	}

	private final AtomicBoolean reportError = new AtomicBoolean(true);

	public LogProvider decorate(final LogProvider binder) {
		if (to == null) {
			if (reportError.getAndSet(false)) {
				LogLog.reportFailure("IllealState: to attribute is [" + to
						+ "]..", null);
			}
			return binder;
		}

		return new LogProvider() {
			public boolean isEnableFor(Level level, Enum<?> logId) {
				if (isTarget(logId)) {
					return binder.isEnableFor(to, logId);
				} else {
					return binder.isEnableFor(level, logId);
				}
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e, Object[] params) {
				if (isTarget(logId)) {
					binder.log(to, logId, message, e, params);
				} else {
					binder.log(level, logId, message, e, params);
				}
			}

			public String getName() {
				return getName();
			}
		};
	}

}
