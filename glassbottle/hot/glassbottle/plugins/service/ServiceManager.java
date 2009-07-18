package glassbottle.plugins.service;

import glassbottle.util.io.WriteTo;

public interface ServiceManager
{
   public WriteTo invokeAndCreateResponseFormatter(String context, String clazz, String method, String type);

}
