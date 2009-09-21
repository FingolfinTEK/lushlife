package org.lushlife.kamikaze;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Info;
import org.lushlife.stla.Warn;

public enum LogMsgCore {

	@Info("JSP page [{}]")
	KMKZ00001,

	@Info("init bean manager")
	KMKZ00002,

	@Info("hot deploy[{}] , update[{}]")
	KMKZ00003,

	@Debug("CurrentManager[{}] {}")
	KMKZ00004,

	@Info("hot deploy classloader create {}")
	KMKZ00005,

	@Warn("Error loading {}")
	KMKZ00006,

	@Warn("Error loading")
	KMKZ00007

}
