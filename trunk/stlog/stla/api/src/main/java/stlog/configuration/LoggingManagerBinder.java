package stlog.configuration;

import stlog.spi.LevelResolver;
import stlog.spi.LocaleSelector;
import stlog.spi.LogProviderDecorator;
import stlog.spi.MessageResolver;

public interface LoggingManagerBinder {

	public void install(LoggingManagerConfiguration config);

	public void localeSelector(LocaleSelector localeSelector);

	public void levelResolver(LevelResolver levelResolver);

	public void addMessageResolver(MessageResolver messageResolver);

	public void addDecorator(LogProviderDecorator decorator);

}
