package stlog.spi;

import stlog.Level;

public interface LogProvider {
	public boolean isEnableFor(Level level, Enum<?> logId);

	public void log(Level level, Enum<?> logId, String message, Throwable e);

	public void log(Level level, Enum<?> logId, String format, Object[] params);

}
