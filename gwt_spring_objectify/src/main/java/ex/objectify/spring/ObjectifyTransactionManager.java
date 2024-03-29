package ex.objectify.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Objectify;

public class ObjectifyTransactionManager extends
		AbstractPlatformTransactionManager {

	static private Logger logger = LoggerFactory
			.getLogger(ObjectifyTransactionManager.class);

	private static final long serialVersionUID = -5199988194360575666L;

	private ObjectifyManager manager;

	@Override
	protected Object doGetTransaction() throws TransactionException {
		logger.info("do get transaction ");
		return manager;
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition)
			throws TransactionException {
		Objectify objectify = ((ObjectifyManager) transaction)
				.getOrCreateObjectifyWithTransaction();
		logger.info("do begin {} {}", objectify.getTxn(), definition);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status)
			throws TransactionException {
		Objectify objectify = manager.getProxy();
		Transaction txn = objectify.getTxn();
		if (txn != null) {
			logger.info("do commit : txn:[{}],status:[{}]", txn, status);
			if (txn.isActive()) {
				txn.commit();
			}
		} else {
			logger.info("do commit with no transaction {}", status);
		}
		manager.clear();
	}

	@Override
	protected void doRollback(DefaultTransactionStatus status)
			throws TransactionException {
		Objectify objectify = (Objectify) manager.getProxy();
		Transaction txn = objectify.getTxn();
		if (txn != null) {
			logger.info("do rollback : txn:[{}] status:[{}] ", txn, status);
			if (txn.isActive()) {
				txn.rollback();
			}
		} else {
			logger.info("do rollback with no transaction : status[{}]", status);
		}
		manager.clear();
	}

	public void setManager(ObjectifyManager objectifyFactory) {
		this.manager = objectifyFactory;
	}

}
