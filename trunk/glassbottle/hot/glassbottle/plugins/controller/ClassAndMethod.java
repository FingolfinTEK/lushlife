package glassbottle.plugins.controller;

import java.lang.reflect.Method;

public class ClassAndMethod
{
   private Class<?> clazz;
   private Method method;

   public ClassAndMethod(Class<?> clazz, Method method)
   {
      super();
      this.clazz = clazz;
      this.method = method;
   }

   public Class<?> getClazz()
   {
      return clazz;
   }

   public Method getMethod()
   {
      return method;
   }

}
