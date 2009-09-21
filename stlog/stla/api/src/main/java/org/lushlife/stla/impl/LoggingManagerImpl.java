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

import java.util.ArrayList;
import java.util.List;

import org.lushlife.stla.configuration.LoggingManagerBinder;
import org.lushlife.stla.configuration.LoggingManagerConfiguration;
import org.lushlife.stla.message.CompositeMessageResolver;
import org.lushlife.stla.spi.LevelResolver;
import org.lushlife.stla.spi.LocaleSelector;
import org.lushlife.stla.spi.LogProviderDecorator;
import org.lushlife.stla.spi.LoggingManager;
import org.lushlife.stla.spi.MessageResolver;


/**
 * @author Takeshi Kondo
 */
public class LoggingManagerImpl implements LoggingManager {
	private CompositeMessageResolver messageResolver = new CompositeMessageResolver();
	private LevelResolver levelResolver;
	private LocaleSelector localeSelector;
	private List<LogProviderDecorator> decorators = new ArrayList<LogProviderDecorator>();
	private LoggingManagerConfiguration config;

	public LoggingManagerImpl(LoggingManagerConfiguration config) {
		this.config = config;
	}

	public List<LogProviderDecorator> getDecorators() {
		return decorators;
	}

	public LevelResolver getLevelResolver() {
		return levelResolver;
	}

	public LocaleSelector getLocaleSelector() {
		return localeSelector;
	}

	public MessageResolver getMessageResolver() {
		return messageResolver;
	}

	public void initialize() {
		config.configure(new LoggingManagerBinder() {

			public void localeSelector(LocaleSelector localeSelector) {
				LoggingManagerImpl.this.localeSelector = localeSelector;
			}

			public void levelResolver(LevelResolver levelResolver) {
				LoggingManagerImpl.this.levelResolver = levelResolver;
			}

			public void addMessageResolver(MessageResolver messageResolver) {
				LoggingManagerImpl.this.messageResolver.add(messageResolver);
			}

			public void addDecorator(LogProviderDecorator decorator) {
				decorators.add(decorator);
			}

			public void install(LoggingManagerConfiguration config) {
				config.configure(this);
			}
		});
	}
}
