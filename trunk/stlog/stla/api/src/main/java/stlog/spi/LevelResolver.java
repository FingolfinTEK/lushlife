package stlog.spi;

import stlog.Level;

public interface LevelResolver {
	<E extends Enum<E>>Level toLevel(E logId);
}
