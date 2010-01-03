package org.lushlife.inject;

import javax.inject.Singleton;

import org.jboss.weld.context.AbstractThreadLocalMapContext;

@Singleton
public class ClientContext extends AbstractThreadLocalMapContext {

	public ClientContext() {
		super(ClientScoped.class);
	}

	@Override
	protected boolean isCreationLockRequired() {
		return false;
	}
}
