package org.lushlife.kamikaze.util.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.lushlife.kamikaze.scope.EventScoped;
import org.lushlife.kamikaze.util.date.StartupTime;

@Singleton
public class RequestIdGenerator {
	@Inject
	@StartupTime
	Long time;

	AtomicInteger counter = new AtomicInteger();

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@RequestId
	@EventScoped
	@Produces
	public String createId() {
		return formatter.format(new Date()) + ":" + counter.getAndIncrement()
				+ ":" + formatter.format(new Date(time));
	}
}
