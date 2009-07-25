package glassbottle2.el;

import javax.enterprise.inject.spi.Bean;

public interface NamingResolver
{
   Bean<?> resolve(String name);
}
