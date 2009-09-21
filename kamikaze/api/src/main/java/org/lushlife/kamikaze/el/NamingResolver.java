package org.lushlife.kamikaze.el;

import javax.enterprise.inject.spi.Bean;

public interface NamingResolver
{
   Bean<?> resolve(String name);
}
