package stlog.configuration;

import stlog.i18n.LocaleSelectorImpl;
import stlog.level.AnnotationLevelResolver;
import stlog.message.AnnotationMessageResolver;
import stlog.message.ResourceBunldeMessageResolver;

public class DefaultLoggingManagerConfiguration extends
		LoggingManagerConfigurationBase {

	@Override
	protected void configure() {
		localeSelector(new LocaleSelectorImpl());
		levelResolver(new AnnotationLevelResolver());

		addMessageResolver(new ResourceBunldeMessageResolver());
		addMessageResolver(new AnnotationMessageResolver());

		// addDecorator(new AddLogIdToMessage());

	}

}
