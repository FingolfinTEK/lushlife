package org.lushlife.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class STLALogProducer {

	@Produces
	public Log getLogger(InjectionPoint injectionPoint) {
		Class<?> declaringClass = injectionPoint.getMember()
				.getDeclaringClass();
		return Logging.getLog(declaringClass);
	}

}
