package glassbottle.plugins.service.dsl;

import glassbottle.plugins.service.TypeFormatter;

public interface TypeFormatBinding
{
   void to(Class<? extends TypeFormatter> formatter);
}
