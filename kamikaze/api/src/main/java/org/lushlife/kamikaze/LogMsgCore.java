package org.lushlife.kamikaze;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Info;

public enum LogMsgCore {

	@Info("JSP page [{}]")
	KMK00001,

	@Info("init bean manager")
	KMK00002,

	@Info("hot deploy[{}] , update[{}]")
	KMK00003,

	@Debug("CurrentManager[{0}] {1}")
	KMK00004

}
