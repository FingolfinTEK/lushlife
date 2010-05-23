package org.lushlife.guicexml.spi;

import java.lang.annotation.Annotation;

public interface ScopeBinding {
	String[] getNames();

	Class<? extends Annotation> getScopeAnnotation();
}
