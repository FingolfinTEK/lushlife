package org.lushlife.stla;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Error;

public enum LogMsg {
	@Debug("Error Message {} {}")
	TEST0001,

	@Info("Block {} {}")
	TEST0002,

	@Warn("Test Message")
	TEST0003

}