package org.lushlife.kamikaze.util.date;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class StartupTimeProducer implements Serializable {
	private static final long serialVersionUID = 998095197676470759L;

	long time;

	public StartupTimeProducer() {
		this.time = System.currentTimeMillis();
	}

	@Produces
	@StartupTime
	public Long startupTime() {
		return time;
	}
}
