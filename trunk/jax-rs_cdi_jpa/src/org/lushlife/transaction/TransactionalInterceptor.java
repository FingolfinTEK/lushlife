package org.lushlife.transaction;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor
@Transactional
public class TransactionalInterceptor {

	@Inject
	private EntityManager entityManager;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext context) throws Exception {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			Object ret = context.proceed();
			transaction.commit();
			return ret;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

	}

}
