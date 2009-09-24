package org.lushlife.kamikaze.spi;

import java.util.Collection;

public interface PostInitializationEvent {

	Collection<Class<?>> getBeanClasses();
}
