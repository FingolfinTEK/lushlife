package lush.plugins.lfunction;

import lushfile.plugins.view.ViewUtilMetadata;

import com.google.inject.Inject;

public class LFunctionConfigurator {

	@Inject
	public void init(ViewUtilMetadata meta) {
		meta.getBindingItems().put("l", LFunction.class);
	}

}
