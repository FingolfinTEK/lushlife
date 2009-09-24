package org.lushlife.negroni;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Error;

/**
 * ログメッセージ
 * 
 * @author Takeshi Kondo
 */
public enum LogMsgNGLN {
	@Error("delegete method not found {0}")
	NGLN00001,

	@Debug("accept [{0},{1}] [{2}]")
	NGLN00002,

	@Debug("Mixin class owner[{0}] mixined[{1}]")
	NGLN00003

}
