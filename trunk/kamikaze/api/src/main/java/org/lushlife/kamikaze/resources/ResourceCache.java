package org.lushlife.kamikaze.resources;

import org.lushlife.kamikaze.util.io.WriteTo;

public interface ResourceCache
{
   WriteTo write(String resource);
}
