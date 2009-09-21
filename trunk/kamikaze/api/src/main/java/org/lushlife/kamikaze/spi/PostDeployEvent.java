package org.lushlife.kamikaze.spi;

import java.util.Collection;

public interface PostDeployEvent {

	Collection<Class<?>> getClasses();
}
