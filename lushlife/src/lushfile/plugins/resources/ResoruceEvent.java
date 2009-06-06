package lushfile.plugins.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import lushfile.core.controller.LushEvent;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;

public class ResoruceEvent implements LushEvent {

	@Inject
	RequestContext context;

	@Inject
	ResourceManager reasourceManager;

	@Inject
	HttpServletResponse response;

	@Override
	public LushEvent fire() {
		try {

			String contextName = context.getControllerName();
			String type = context.getMethodName();
			String resoruceName = context.getParameter()[0];
			reasourceManager.write(contextName, type, resoruceName)//
					.to(response.getOutputStream());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}
