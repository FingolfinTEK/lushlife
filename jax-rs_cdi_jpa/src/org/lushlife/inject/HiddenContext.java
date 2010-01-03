package org.lushlife.inject;

import javax.inject.Singleton;

import org.jboss.weld.context.AbstractThreadLocalMapContext;

@Singleton
public class HiddenContext extends AbstractThreadLocalMapContext {

	public HiddenContext() {
		super(HiddenScoped.class);
	}

	@Override
	protected boolean isCreationLockRequired() {
		return false;
	}
}
