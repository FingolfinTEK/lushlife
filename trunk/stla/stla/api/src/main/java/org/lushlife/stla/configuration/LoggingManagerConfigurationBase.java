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
package org.lushlife.stla.configuration;

import org.lushlife.stla.spi.LevelResolver;
import org.lushlife.stla.spi.LocaleSelector;
import org.lushlife.stla.spi.LogProviderDecorator;
import org.lushlife.stla.spi.MessageResolver;

/**
 * @author Takeshi Kondo
 */
public abstract class LoggingManagerConfigurationBase implements
		LoggingManagerBinder, LoggingManagerConfiguration {
	private LoggingManagerBinder binder;

	public void addDecorator(LogProviderDecorator decorator) {
		binder.addDecorator(decorator);
	}

	public void addMessageResolver(MessageResolver messageResolver) {
		binder.addMessageResolver(messageResolver);
	}

	public void levelResolver(LevelResolver levelResolver) {
		binder.levelResolver(levelResolver);
	}

	public void localeSelector(LocaleSelector localeSelectorClass) {
		binder.localeSelector(localeSelectorClass);
	}

	public void configure(LoggingManagerBinder bidner) {
		this.binder = bidner;
		configure();
	}

	abstract protected void configure();

	public void install(LoggingManagerConfiguration config) {
		binder.install(config);
	}

}
