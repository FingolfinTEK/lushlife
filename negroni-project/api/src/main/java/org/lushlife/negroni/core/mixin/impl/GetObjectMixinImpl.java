package org.lushlife.negroni.core.mixin.impl;

import org.lushlife.negroni.annotation.Mixined;

public class GetObjectMixinImpl<T> {

	@Mixined
	public T getMixinObject(T obj) {
		return obj;
	}

}
