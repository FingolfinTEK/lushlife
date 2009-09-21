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

import com.google.code.lushlife.stla.spi.LevelResolver;
import com.google.code.lushlife.stla.spi.LocaleSelector;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;
import com.google.code.lushlife.stla.spi.MessageResolver;

/**
 * @author Takeshi Kondo
 */
public interface LoggingManagerBinder {

	public void install(LoggingManagerConfiguration config);

	public void localeSelector(LocaleSelector localeSelector);

	public void levelResolver(LevelResolver levelResolver);

	public void addMessageResolver(MessageResolver messageResolver);

	public void addDecorator(LogProviderDecorator decorator);

}
