package org.lushlife.negroni.core.mixin;

import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.core.mixin.impl.GetObjectMixinImpl;

@MixinImplementedBy(GetObjectMixinImpl.class)
public interface GetObjectMixin<T> {
	T getMixinObject();
}
