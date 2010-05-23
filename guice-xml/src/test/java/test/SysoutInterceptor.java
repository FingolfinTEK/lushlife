package test;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class SysoutInterceptor {
	String message = "system out";

	@AroundInvoke
	public Object print(InvocationContext context) throws Exception {
		System.out.println(message + " " + context.getMethod());
		return context.proceed();
	}
}
