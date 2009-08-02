package glassbottle2.util.id;

import glassbottle2.binding.StartupTime;
import glassbottle2.scope.EventScoped;
import glassbottle2.scope.Singleton;
import glassbottle2.util.id.binding.RequestId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Named;
import javax.enterprise.inject.Produces;

@Singleton
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
