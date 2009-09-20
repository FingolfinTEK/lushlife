package com.google.code.lushlife.stla.configuration;

import com.google.code.lushlife.stla.spi.LevelResolver;
import com.google.code.lushlife.stla.spi.LocaleSelector;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;
import com.google.code.lushlife.stla.spi.MessageResolver;

public interface LoggingManagerBinder {

	public void install(LoggingManagerConfiguration config);

	public void localeSelector(LocaleSelector localeSelector);

	public void levelResolver(LevelResolver levelResolver);

	public void addMessageResolver(MessageResolver messageResolver);

	public void addDecorator(LogProviderDecorator decorator);

}
