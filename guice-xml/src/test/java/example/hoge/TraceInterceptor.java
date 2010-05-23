package example.hoge;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class TraceInterceptor {

	String message;

	/**
	 * EJB3 Interface interceptor
	 */
	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception {
		System.out.println("trace " + context.getMethod() + " : message "
				+ message);
		return context.proceed();
	}
}
