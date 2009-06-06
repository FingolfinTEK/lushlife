package lushfile.core.jdo;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

public abstract class Transactional {
	PersistenceManager pm;

	public Transactional(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	abstract protected void transactional();

	public void invoke() {
		Transaction tra = pm.currentTransaction();
		try {
			tra.begin();
			transactional();
			tra.commit();
		} catch (RuntimeException e) {
			tra.rollback();
			throw e;
		}
	}

}
