package stla.decorator;

import stla.spi.LogProviderDecorator;

public abstract class LogIdHandlingDecoratorBase extends
		LogProviderDecoratorBase implements LogProviderDecorator {
	protected String logId;

	public void setLogId(String value) {
		this.logId = value;
	}

	protected boolean isTarget(Enum<?> logId) {
		if (this.logId == null) {
			return true;
		}
		if (!logId.name().equals(this.logId)) {
			return false;
		}
		return true;
	}

}
