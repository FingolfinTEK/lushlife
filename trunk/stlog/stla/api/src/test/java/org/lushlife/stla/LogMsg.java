package org.lushlife.stla;

import org.lushlife.stla.Debug;
import org.lushlife.stla.Error;


public enum LogMsg {
	@Debug("Error Message {} {}")
	TEST0001,
	
	@Error("Block {} {}")
	TEST0002,
	

	@Error("Test Message")
	TEST0003
	
}