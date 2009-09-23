package negroni.core.mixin;

import negroni.annotation.MixinImplementedBy;
import negroni.core.mixin.impl.GetObjectMixinImpl;

@MixinImplementedBy(GetObjectMixinImpl.class)
public interface GetObjectMixin<T> {
	T getMixinObject();
}
