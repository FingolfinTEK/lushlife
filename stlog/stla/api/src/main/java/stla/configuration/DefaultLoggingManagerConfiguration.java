package stla.configuration;

import stla.i18n.LocaleSelectorImpl;
import stla.level.AnnotationLevelResolver;
import stla.message.AnnotationMessageResolver;
import stla.message.ResourceBunldeMessageResolver;

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
