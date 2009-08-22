package org.slf4j.i18n;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.i18n.annotations.Debug;
import org.slf4j.i18n.annotations.Error;
import org.slf4j.i18n.annotations.Info;
import org.slf4j.i18n.annotations.Message;
import org.slf4j.i18n.annotations.Trace;
import org.slf4j.i18n.annotations.Warn;

public class I18NLoggerTest {

	enum LogMessages {
		@Error("error")
		ETEST,

		@Warn("warn")
		WTEST,

		@Info("info")
		ITEST,

		@Debug("debug")
		DTEST,

		@Trace("trace")
		TTEST,

		@Message("message")
		NTEST,

		@Error("error")
		OETEST,

		@Warn("warn")
		OWTEST,

		@Info("info")
		OITEST,

		@Debug("debug")
		ODTEST,

		@Trace("trace")
		OTTEST,

		@Message("message")
		ONTEST
	}

	@Test
	public void testIsEnableFor() {
		I18NLogger logger = I18NLoggerFactory.getLogger(I18NLoggerTest.class);

		Assert.assertTrue(logger.isEnabledFor(LogMessages.ETEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.WTEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.ITEST));
		Assert.assertFalse(logger.isEnabledFor(LogMessages.DTEST));
		Assert.assertFalse(logger.isEnabledFor(LogMessages.TTEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.NTEST));

		Assert.assertTrue(logger.isErrorEnabled(LogMessages.NTEST));
		Assert.assertTrue(logger.isWarnEnabled(LogMessages.NTEST));
		Assert.assertTrue(logger.isInfoEnabled(LogMessages.NTEST));
		Assert.assertFalse(logger.isDebugEnabled(LogMessages.NTEST));
		Assert.assertFalse(logger.isTraceEnabled(LogMessages.NTEST));

		Assert.assertTrue(logger.isErrorEnabled(LogMessages.ITEST));
		Assert.assertTrue(logger.isWarnEnabled(LogMessages.ITEST));
		Assert.assertTrue(logger.isInfoEnabled(LogMessages.ITEST));
		Assert.assertTrue(logger.isDebugEnabled(LogMessages.ITEST));
		Assert.assertTrue(logger.isTraceEnabled(LogMessages.ITEST));

	}

	@Test
	public void testIsEnableForOverWrite() {
		I18NLogger logger = I18NLoggerFactory.getLogger(I18NLoggerTest.class);

		Assert.assertTrue(logger.isEnabledFor(LogMessages.OETEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.OWTEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.OITEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.ODTEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.OTTEST));
		Assert.assertTrue(logger.isEnabledFor(LogMessages.ONTEST));

		Assert.assertTrue(logger.isErrorEnabled(LogMessages.ONTEST));
		Assert.assertTrue(logger.isWarnEnabled(LogMessages.ONTEST));
		Assert.assertTrue(logger.isInfoEnabled(LogMessages.ONTEST));
		Assert.assertTrue(logger.isDebugEnabled(LogMessages.ONTEST));
		Assert.assertTrue(logger.isTraceEnabled(LogMessages.ONTEST));

		Assert.assertTrue(logger.isErrorEnabled(LogMessages.OITEST));
		Assert.assertTrue(logger.isWarnEnabled(LogMessages.OITEST));
		Assert.assertTrue(logger.isInfoEnabled(LogMessages.OITEST));
		Assert.assertTrue(logger.isDebugEnabled(LogMessages.OITEST));
		Assert.assertTrue(logger.isTraceEnabled(LogMessages.OITEST));

	}

	@Test
	public void testLog() {
		I18NLogger logger = I18NLoggerFactory.getLogger(I18NLoggerTest.class);
		LoggingEvent event;

		logger.log(LogMessages.ETEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.ERROR, event.getLevel());

		logger.log(LogMessages.WTEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.WARN, event.getLevel());

		logger.log(LogMessages.ITEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.INFO, event.getLevel());

		logger.log(LogMessages.DTEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.INFO, event.getLevel());

		logger.log(LogMessages.TTEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.INFO, event.getLevel());

	}

	@Test
	public void testLogOverWrite() {
		I18NLogger logger = I18NLoggerFactory.getLogger(I18NLoggerTest.class);
		LoggingEvent event;

		logger.log(LogMessages.OETEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.ERROR, event.getLevel());

		logger.log(LogMessages.OWTEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.ERROR, event.getLevel());

		logger.log(LogMessages.OITEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.ERROR, event.getLevel());

		logger.log(LogMessages.ODTEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.ERROR, event.getLevel());

		logger.log(LogMessages.OTTEST);
		event = EventCaptureAppender.getLastEvent();
		Assert.assertEquals(Level.ERROR, event.getLevel());

	}

}
