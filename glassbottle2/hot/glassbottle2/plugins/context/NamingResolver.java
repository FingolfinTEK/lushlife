package glassbottle2.plugins.context;

import javax.enterprise.inject.spi.Bean;

public interface NamingResolver
{
   Bean<?> resolve(String name);
}
