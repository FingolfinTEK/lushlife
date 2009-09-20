package com.google.code.lushlife.stla;

import org.junit.Test;

import com.google.code.lushlife.stla.Log;
import com.google.code.lushlife.stla.Logging;


public class LoggerTest {
	static Log logger = Logging.getLogger(LoggerTest.class);

	@Test
	public void test_logger() {
		logger.log(LogMsg.TEST0001, "xx", "yy");
		logger.log(LogMsg.TEST0001, new NullPointerException());
		
		logger.log(LogMsg.TEST0002, "xx", "yy");
		logger.log(LogMsg.TEST0002, new NullPointerException());
		
		logger.log(LogMsg.TEST0003, "xx", "yy");
		logger.log(LogMsg.TEST0003, new NullPointerException());
		
		
	}

}
