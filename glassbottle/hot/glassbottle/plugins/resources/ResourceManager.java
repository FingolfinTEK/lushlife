package glassbottle.plugins.resources;

import glassbottle.util.io.WriteTo;

public interface ResourceManager
{

   WriteTo write(String context, String type, String resource);

   String createUrl(String context, String type, String resource);
}
