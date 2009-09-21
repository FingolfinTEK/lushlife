package org.lushlife.kamikaze;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Info;

public enum LogMsgCore {

	@Info("JSP page [{}]")
	KMKZ00001,

	@Info("init bean manager")
	KMKZ00002,

	@Info("hot deploy[{}] , update[{}]")
	KMKZ00003,

	@Debug("CurrentManager[{}] {}")
	KMKZ00004

}
