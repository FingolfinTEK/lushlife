package lush.application.lushup.action;

import lush.application.lushup.model.TestDto;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;

public class Lushup {

	@Inject
	RequestContext parameter;

	@Inject
	TestDto dto;

	public void invoke() {
		dto.setValue(parameter.getContextName() + "#"
				+ parameter.getControllerName() + parameter.getMethodName());
	}

}
