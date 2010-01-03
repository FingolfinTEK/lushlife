package org.lushlife.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public abstract class EntityManagerDelegate implements EntityManager,
		Serializable {

	private static final long serialVersionUID = -5216479100183861796L;
	private EntityManager delegate;

	protected EntityManager delegate() {
		return delegate;
	}

	public EntityManagerDelegate() {
	}

	public EntityManagerDelegate(EntityManager delegate) {
		this.delegate = delegate;
	}

	public void clear() {
		delegate.clear();
	}

	public void close() {
		delegate.close();
	}

	public boolean contains(Object arg0) {
		return delegate.contains(arg0);
	}

	public Query createNamedQuery(String arg0) {
		return delegate.createNamedQuery(arg0);
	}

	public Query createNativeQuery(String arg0, Class arg1) {
		return delegate.createNativeQuery(arg0, arg1);
	}

	public Query createNativeQuery(String arg0, String arg1) {
		return delegate.createNativeQuery(arg0, arg1);
	}

	public Query createNativeQuery(String arg0) {
		return delegate.createNativeQuery(arg0);
	}

	public Query createQuery(String arg0) {
		return delegate.createQuery(arg0);
	}

	public <T> T find(Class<T> arg0, Object arg1) {
		return delegate.find(arg0, arg1);
	}

	public void flush() {
		delegate.flush();
	}

	public Object getDelegate() {
		return delegate.getDelegate();
	}

	public FlushModeType getFlushMode() {
		return delegate.getFlushMode();
	}

	public <T> T getReference(Class<T> arg0, Object arg1) {
		return delegate.getReference(arg0, arg1);
	}

	public EntityTransaction getTransaction() {
		return delegate.getTransaction();
	}

	public boolean isOpen() {
		return delegate.isOpen();
	}

	public void joinTransaction() {
		delegate.joinTransaction();
	}

	public void lock(Object arg0, LockModeType arg1) {
		delegate.lock(arg0, arg1);
	}

	public <T> T merge(T arg0) {
		return delegate.merge(arg0);
	}

	public void persist(Object arg0) {
		delegate.persist(arg0);
	}

	public void refresh(Object arg0) {
		delegate.refresh(arg0);
	}

	public void remove(Object arg0) {
		delegate.remove(arg0);
	}

	public void setFlushMode(FlushModeType arg0) {
		delegate.setFlushMode(arg0);
	}

}
