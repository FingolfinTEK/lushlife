package glassbottle2.util.loader;

import java.io.InputStream;

public class ClassLoaderUtil
{

   static public String toClassPath(String packageName, String resourceName)
   {
      return packageName.replace(".", "/") + "/" + resourceName;
   }

   static public AsStream load(ClassLoader loader, String packageName, String resoruceName)
   {
      String clazzPath = toClassPath(packageName, resoruceName);
      return load(loader, clazzPath);
   }

   public static AsStream load(ClassLoader loader, String clazzPath)
   {
      InputStream stream = loader.getResourceAsStream(clazzPath);
      if (stream == null)
      {
         throw new IllegalStateException("resoruce not found " + clazzPath);
      }
      return new AsStream(stream);
   }
}
