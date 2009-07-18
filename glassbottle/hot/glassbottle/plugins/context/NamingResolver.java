package glassbottle.plugins.context;

import javax.enterprise.inject.spi.Bean;

public interface NamingResolver
{
   Bean<?> resolve(String name);
}
