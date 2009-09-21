package org.lushlife.kamikaze.extension.jspquery.tag;

public interface Tag<T extends Tag<T>>
{
   T attr(String name, Object value);

   Object attr(String name);

   String tag();

}
