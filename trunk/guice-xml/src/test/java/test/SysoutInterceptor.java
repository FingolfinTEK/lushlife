package test;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class SysoutInterceptor {

	@AroundInvoke
	public Object print(InvocationContext context) throws Exception {
		System.out.println("system out " + context.getMethod());
		return context.proceed();
	}
}
