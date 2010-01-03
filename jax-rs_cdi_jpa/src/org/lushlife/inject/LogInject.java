package org.lushlife.inject;

import org.lushlife.stla.Info;

public enum LogInject {

	@Info("add non-proxy scopes")
	ADD_SCOPE,

	@Info("begin request")
	BEGIN_REQUEST,

	@Info("end request")
	END_REQUEST,

}
