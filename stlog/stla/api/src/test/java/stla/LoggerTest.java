package stla;

import org.junit.Test;

import stla.Log;
import stla.Logging;

public class LoggerTest {
	static Log logger = Logging.getLogger(LoggerTest.class);

	@Test
	public void test_logger() {
		logger.log(LogMsg.TEST0001, "xx", "yy");
		logger.log(LogMsg.TEST0001, new NullPointerException());
		
		logger.log(LogMsg.TEST0002, "xx", "yy");
		logger.log(LogMsg.TEST0002, new NullPointerException());
		
	}

}
