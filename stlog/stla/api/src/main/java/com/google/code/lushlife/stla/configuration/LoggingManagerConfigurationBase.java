package com.google.code.lushlife.stla.configuration;

import com.google.code.lushlife.stla.spi.LevelResolver;
import com.google.code.lushlife.stla.spi.LocaleSelector;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;
import com.google.code.lushlife.stla.spi.MessageResolver;

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
