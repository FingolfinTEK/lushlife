package glassbottle2.util.id;

import glassbottle2.binding.StartupTime;
import glassbottle2.scope.EventScoped;
import glassbottle2.util.id.binding.RequestId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

@javax.inject.Singleton
public class RequestIdGenerator
{
   @StartupTime
   Long time;
   AtomicInteger counter = new AtomicInteger();

   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

   @Named("requestId")
   @RequestId
   @EventScoped
   @Produces
   public String createId()
   {
      return formatter.format(new Date()) + ":" + counter.getAndIncrement() + ":" + formatter.format(new Date(time));
   }
}
