package example.org.lushlife.stla;

import org.junit.Test;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class LoggerTest {
	static Log logger = Logging.getLog(LoggerTest.class);

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
