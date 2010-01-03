package org.lushlife.mvc;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.lushlife.inject.Injector;

public class JSPPageManager implements Serializable {

	public Object writeReplace() throws ObjectStreamException {
		return this;
	}

	@Inject
	Injector injector;

	public JSPPage forward(String page, Map<String, Object> requestContext) {
		return injector.getInstnace(JSPPage.class).init(page, requestContext);
	}

	public JSPPage forward(String page) {
		return forward(page, new HashMap<String, Object>());
	}

}
