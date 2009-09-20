package com.google.code.lushlife.stla;

import com.google.code.lushlife.stla.Debug;
import com.google.code.lushlife.stla.Error;


public enum LogMsg {
	@Debug("Error Message {} {}")
	TEST0001,
	
	@Error("Block {} {}")
	TEST0002,
	

	@Error("Test Message")
	TEST0003
	
}