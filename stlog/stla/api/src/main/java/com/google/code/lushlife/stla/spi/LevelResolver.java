package com.google.code.lushlife.stla.spi;

import com.google.code.lushlife.stla.Level;

public interface LevelResolver {
	<E extends Enum<E>>Level toLevel(E logId);
}
