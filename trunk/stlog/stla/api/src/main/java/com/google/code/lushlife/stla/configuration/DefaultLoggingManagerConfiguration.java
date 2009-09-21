/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.google.code.lushlife.stla.configuration;

import com.google.code.lushlife.stla.i18n.LocaleSelectorImpl;
import com.google.code.lushlife.stla.level.AnnotationLevelResolver;
import com.google.code.lushlife.stla.message.AnnotationMessageResolver;
import com.google.code.lushlife.stla.message.ResourceBunldeMessageResolver;

/**
 * @author Takeshi Kondo
 */
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
