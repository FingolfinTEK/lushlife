package com.google.code.lushlife.stla.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.code.lushlife.stla.configuration.LoggingManagerBinder;
import com.google.code.lushlife.stla.configuration.LoggingManagerConfiguration;
import com.google.code.lushlife.stla.message.CompositeMessageResolver;
import com.google.code.lushlife.stla.spi.LevelResolver;
import com.google.code.lushlife.stla.spi.LocaleSelector;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;
import com.google.code.lushlife.stla.spi.LoggingManager;
import com.google.code.lushlife.stla.spi.MessageResolver;


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
