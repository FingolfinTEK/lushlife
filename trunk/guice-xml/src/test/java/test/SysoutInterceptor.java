package test;

import javax.annotation.PostConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class SysoutInterceptor {
	String message = "system out";

	@PostConstruct
	public void post() {
		System.out.println("post construct " + message);
	}

	@AroundInvoke
	public Object print(InvocationContext context) throws Exception {
		System.out.println(message + " " + context.getMethod());
		return context.proceed();
	}
}
