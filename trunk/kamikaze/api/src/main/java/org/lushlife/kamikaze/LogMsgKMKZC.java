package org.lushlife.kamikaze;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Error;
import org.lushlife.stla.Info;
import org.lushlife.stla.Warn;

public enum LogMsgKMKZC {

	@Info("JSP page [{0}]")
	KMKZC0001,

	@Info("init bean manager: modules[{0}]")
	KMKZC0002,

	@Info("hot deploy[{0}] , update[{1}]")
	KMKZC0003,

	@Debug("CurrentManager[{0}] {1}")
	KMKZC0004,

	@Info("hot deploy classloader create {0}")
	KMKZC0005,

	@Warn("Error loading {0}")
	KMKZC0006,

	@Warn("Error loading")
	KMKZC0007,

	@Debug("load class {0} ")
	KMKZC0008,

	@Error("Uninitialized BeanManager")
	KMKZC0009,

	@Error("bean not found {0}")
	KMKZC0010

}
