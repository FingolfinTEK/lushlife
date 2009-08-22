package org.slf4j.i18n.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.i18n.spi.LogMessageFormatResolver;

public class CompositeMessageFormatResolver implements LogMessageFormatResolver {
	private List<LogMessageFormatResolver> resolvers = new ArrayList<LogMessageFormatResolver>();

	public List<LogMessageFormatResolver> getResolvers() {
		return resolvers;
	}

	public void setResolvers(List<LogMessageFormatResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public <E extends Enum<E>> String toMessageFormat(E logId) {
		for (LogMessageFormatResolver resolver : resolvers) {
			String value = resolver.toMessageFormat(logId);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	public void add(LogMessageFormatResolver resolver) {
		resolvers.add(resolver);
	}

}
