package org.lushlife.log;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Trace
public class TraceInterceptor {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext context) throws Exception {
		Logger logger = LoggerFactory.getLogger(context.getTarget().getClass());
		if (!logger.isInfoEnabled()) {
			return context.proceed();
		}
		long start = System.currentTimeMillis();
		try {
			logger.info("begin " + context.getMethod());
			Object ret = context.proceed();
			logger.info("end " + context.getMethod() + " time: "
					+ (System.currentTimeMillis() - start));
			return ret;
		} catch (Exception e) {
			logger.info("throw exception " + context.getMethod() + " time: "
					+ (System.currentTimeMillis() - start), e);
			throw e;
		}

	}

}
