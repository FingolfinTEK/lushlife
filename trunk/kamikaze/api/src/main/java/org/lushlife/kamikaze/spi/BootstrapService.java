package org.lushlife.kamikaze.spi;

import org.lushlife.kamikaze.WebBeansModule;

public interface BootstrapService {

	void shutdownManager();

	void bootManager(Iterable<WebBeansModule> modules);

}
