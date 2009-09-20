package com.google.code.lushlife.stla.configuration;

import com.google.code.lushlife.stla.i18n.LocaleSelectorImpl;
import com.google.code.lushlife.stla.level.AnnotationLevelResolver;
import com.google.code.lushlife.stla.message.AnnotationMessageResolver;
import com.google.code.lushlife.stla.message.ResourceBunldeMessageResolver;

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
