package org.lushlife.kamikaze;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Error;
import org.lushlife.stla.Info;
import org.lushlife.stla.Warn;

public enum LogMsgKMKZC {

	@Info("JSP page [{}]")
	KMKZC0001,

	@Info("init bean manager: modules[{}]")
	KMKZC0002,

	@Info("hot deploy[{}] , update[{}]")
	KMKZC0003,

	@Debug("CurrentManager[{}] {}")
	KMKZC0004,

	@Info("hot deploy classloader create {}")
	KMKZC0005,

	@Warn("Error loading {}")
	KMKZC0006,

	@Warn("Error loading")
	KMKZC0007,

	@Debug("load class {0} ")
	KMKZC0008,

	@Error("Uninitialized BeanManager")
	KMKZC0009

}
