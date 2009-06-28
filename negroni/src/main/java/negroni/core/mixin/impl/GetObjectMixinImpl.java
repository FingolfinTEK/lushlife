package negroni.core.mixin.impl;

import negroni.annotation.Mixined;

public class GetObjectMixinImpl<T> {

	@Mixined
	public T getMixinObject(T obj) {
		return obj;
	}

}
