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
package org.lushlife.stla.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lushlife.stla.spi.MessageResolver;


/**
 * @author Takeshi Kondo
 */
public class CompositeMessageResolver implements MessageResolver {

	List<MessageResolver> resolvers = new ArrayList<MessageResolver>();

	public <E extends Enum<E>> String toMessage(E logId, Locale locale) {
		for (MessageResolver resolver : resolvers) {
			String message = resolver.toMessage(logId, locale);
			if (message != null) {
				return message;
			}
		}
		return null;
	}

	public void add(MessageResolver resolver) {
		this.resolvers.add(resolver);
	}

}
