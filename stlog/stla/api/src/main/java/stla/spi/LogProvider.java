package stla.spi;

import stla.Level;

public interface LogProvider {
	public String getName();

	public boolean isEnableFor(Level level, Enum<?> logId);

	public void log(Level level, Enum<?> logId, String message, Throwable e);

	public void log(Level level, Enum<?> logId, String format, Object[] params);

}
