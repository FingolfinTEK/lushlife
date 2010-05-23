package test;

import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.el.ThreadLocalContexts;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SysoutInterceptor {

	@Inject
	Provider<Expressions> expressions;

	ValueExpression message = Expressions.createValueExpression("system out");

	@PostConstruct
	public void post() {
		System.out.println("post construct ");
	}

	@AroundInvoke
	public Object print(InvocationContext context) throws Exception {
		new ThreadLocalContexts.Put<RuntimeException>("invocationContext",
				context) {
			@Override
			public void call() throws RuntimeException {
				System.out.println(message.getValue(expressions.get()
						.createELContext()));
			}
		};
		return context.proceed();
	}
}
