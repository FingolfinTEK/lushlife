package org.lushlife.kamikaze.jboss;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Info;

public enum LogMsgJBoss {

	@Info("request context initialized")
	KMKZJ0001,

	@Info("request context destoryed ")
	KMKZJ0002,

	@Debug("load bean module [{}]")
	KMKZJ0003,

	@Debug("load class {0} ")
	KMKZJ0004,

}
