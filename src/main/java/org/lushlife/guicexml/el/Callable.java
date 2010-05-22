package org.lushlife.guicexml.el;

public interface Callable<E extends Throwable> {

	public void call() throws E;

}
