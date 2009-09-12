package stlog.impl;

import java.util.ArrayList;
import java.util.List;

import stlog.decorator.AddLogIdToMessage;
import stlog.i18n.DefaultLocaleSelector;
import stlog.level.AnnotationLevelResolver;
import stlog.message.AnnotationMessageResolver;
import stlog.message.CompositeMessageResolver;
import stlog.message.ResourceBunldeMessageResolver;
import stlog.spi.LevelResolver;
import stlog.spi.LocaleSelector;
import stlog.spi.LogProviderDecorator;
import stlog.spi.LoggingManager;
import stlog.spi.MessageResolver;

public class LoggingManagerImpl implements LoggingManager {
	private CompositeMessageResolver messageResolver;
	private LevelResolver revelResolver = new AnnotationLevelResolver();
	private LocaleSelector localeSelector = new DefaultLocaleSelector();
	private List<LogProviderDecorator> decorators = new ArrayList<LogProviderDecorator>();

	public List<LogProviderDecorator> getDecorators() {
		return decorators;
	}

	public LevelResolver getLevelResolver() {
		return revelResolver;
	}

	public LocaleSelector getLocaleSelector() {
		return localeSelector;
	}

	public MessageResolver getMessageResolver() {
		return messageResolver;
	}

	public void initialize() {
		messageResolver = new CompositeMessageResolver();
		messageResolver.add(new ResourceBunldeMessageResolver());
		messageResolver.add(new AnnotationMessageResolver());

		this.decorators.add(new AddLogIdToMessage());
	}

	public void clear() {

	}

}
