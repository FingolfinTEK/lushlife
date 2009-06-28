package negroni.sample;

import negroni.annotation.MixinImplementedBy;

@MixinImplementedBy(AMixinImpl.class)
public interface A {
	public void invoke();

}
