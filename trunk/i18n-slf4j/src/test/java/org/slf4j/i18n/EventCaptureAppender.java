package org.slf4j.i18n;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.NullAppender;

public class EventCaptureAppender extends NullAppender
{
   private static LoggingEvent lastEvent;
   
   @Override
   public void doAppend(LoggingEvent event)
   {
      lastEvent = event;
   }

   public static LoggingEvent getLastEvent()
   {
      return lastEvent;
   }

}
