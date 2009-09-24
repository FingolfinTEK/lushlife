package org.lushlife.kamikaze.context;

public interface Context<T> {

	public void set(String name, Object obj);

	public Object get(String name);

	public T getDelegate();
}
