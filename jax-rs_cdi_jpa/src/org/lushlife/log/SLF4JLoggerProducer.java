package org.lushlife.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLoggerProducer {

	@Produces
	public Logger getLogger(InjectionPoint injectionPoint) {
		Class<?> declaringClass = injectionPoint.getMember()
				.getDeclaringClass();
		return LoggerFactory.getLogger(declaringClass);
	}

}
