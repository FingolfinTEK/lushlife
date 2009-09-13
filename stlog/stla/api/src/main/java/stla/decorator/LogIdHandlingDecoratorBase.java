package stla.decorator;

import java.util.concurrent.atomic.AtomicBoolean;

import stla.spi.LogProviderDecorator;
import stla.util.LogLog;

public abstract class LogIdHandlingDecoratorBase extends
		LogProviderDecoratorBase implements LogProviderDecorator {
	protected String enumClass;
	protected String value;

	public void setValue(String value) {
		this.value = value;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

	AtomicBoolean reportError = new AtomicBoolean(true);

	protected boolean validate() {
		if (enumClass == null || value == null) {
			if (reportError.getAndSet(false)) {
				LogLog.reportFailure("IllealState: Ignore "
						+ this.getClass().getSimpleName() + " logId[ "
						+ enumClass + "#" + value + "]", null);
			}
			return false;
		}
		return true;
	}

	protected boolean isTarget(Enum<?> logId) {
		if (!logId.getDeclaringClass().getName().equals(enumClass)) {
			return false;
		}
		if (!logId.name().equals(value)) {
			return false;
		}
		return true;
	}

}
