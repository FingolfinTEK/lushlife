package stlog.impl;

import java.util.ArrayList;
import java.util.List;

import stlog.configuration.LoggingManagerBinder;
import stlog.configuration.LoggingManagerConfiguration;
import stlog.message.CompositeMessageResolver;
import stlog.spi.LevelResolver;
import stlog.spi.LocaleSelector;
import stlog.spi.LogProviderDecorator;
import stlog.spi.LoggingManager;
import stlog.spi.MessageResolver;

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

	public void clear() {

	}

}
