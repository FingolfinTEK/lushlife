package stla.spi;

import stla.Level;

public interface LevelResolver {
	<E extends Enum<E>>Level toLevel(E logId);
}
