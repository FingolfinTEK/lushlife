package stlog;

import org.junit.Test;

public class LoggerTest {
	static Log logger = Logging.getLogger(LoggerTest.class);

	@Test
	public void test_logger() {
		logger.log(LogMsg.TEST0001, "xx");
	}

}
