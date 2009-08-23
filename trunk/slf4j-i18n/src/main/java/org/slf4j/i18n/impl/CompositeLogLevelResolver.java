package org.slf4j.i18n.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.i18n.LogLevel;
import org.slf4j.i18n.spi.LogLevelResolver;

public class CompositeLogLevelResolver implements LogLevelResolver {
	private List<LogLevelResolver> resolvers = new ArrayList<LogLevelResolver>();

	public <E extends Enum<E>> LogLevel toLogLevel(E logId) {
		for (LogLevelResolver resolver : resolvers) {
			LogLevel value = resolver.toLogLevel(logId);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	public List<LogLevelResolver> getResolvers() {
		return resolvers;
	}

	public void setResolvers(List<LogLevelResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public void add(LogLevelResolver resolver) {
		resolvers.add(resolver);
	}

}
