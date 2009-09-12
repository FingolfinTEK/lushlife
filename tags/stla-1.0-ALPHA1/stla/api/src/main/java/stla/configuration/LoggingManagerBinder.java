package stla.configuration;

import stla.spi.LevelResolver;
import stla.spi.LocaleSelector;
import stla.spi.LogProviderDecorator;
import stla.spi.MessageResolver;

public interface LoggingManagerBinder {

	public void install(LoggingManagerConfiguration config);

	public void localeSelector(LocaleSelector localeSelector);

	public void levelResolver(LevelResolver levelResolver);

	public void addMessageResolver(MessageResolver messageResolver);

	public void addDecorator(LogProviderDecorator decorator);

}
