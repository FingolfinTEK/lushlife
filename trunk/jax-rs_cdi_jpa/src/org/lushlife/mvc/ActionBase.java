package org.lushlife.mvc;

import java.util.Map;

import javax.inject.Inject;

public abstract class ActionBase {
	@Inject
	JSPPageManager pageManager;

	protected JSPPage forward(String page, Map<String, Object> requestContext) {
		return pageManager.forward(page, requestContext);
	}

	protected JSPPage forward(String page) {
		return pageManager.forward(page);
	}

}
