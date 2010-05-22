package org.lushlife.guicexml.xml;

import java.lang.annotation.Annotation;

public interface ScopeBinding {
	String[] getNames();

	Class<? extends Annotation> getScopeAnnotation();
}
