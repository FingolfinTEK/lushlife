package lush.application.wiki.action;

import java.util.Arrays;

import lush.application.wiki.model.TestDto;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;

public class Default {

	@Inject
	RequestContext parameter;

	@Inject
	TestDto dto;

	public void invoke() {
		dto.setValue(parameter.getContextName() + "#"
				+ parameter.getControllerName()  +parameter.getMethodName());
	}

}
