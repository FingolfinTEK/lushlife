package com.google.code.lushlife.stla.decorator;

import com.google.code.lushlife.stla.Level;
import com.google.code.lushlife.stla.spi.LogProvider;
import com.google.code.lushlife.stla.spi.LogProviderDecorator;

public class AddLogIdToMessage extends LogProviderDecoratorBase implements
		LogProviderDecorator {

	public LogProvider decorate(final LogProvider proceed) {
		return new LogProvider() {
			public void log(Level level, Enum<?> logId, String format,
					Object[] params) {
				proceed.log(level, logId, "<" + logId.name() + "> " + format,
						params);
			}

			public void log(Level level, Enum<?> logId, String message,
					Throwable e) {
				proceed.log(level, logId, "<" + logId.name() + "> " + message,
						e);
			}

			public boolean isEnableFor(Level level, Enum<?> logId) {
				return proceed.isEnableFor(level, logId);
			}

			public String getName() {
				return proceed.getName();
			}
		};
	}

}
