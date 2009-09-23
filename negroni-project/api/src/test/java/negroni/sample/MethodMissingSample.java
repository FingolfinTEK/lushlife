package negroni.sample;

import java.lang.reflect.Method;

import org.lushlife.negroni.annotation.MethodMissing;


public abstract class MethodMissingSample
{
   @MethodMissing
   public void methodMissing(Method m, String obj)
   {
      System.out.println("call String \t" + m + "\t" + obj);
   }

   @MethodMissing
   public void methodMissing(Method m, Object... obj)
   {
      System.out.println("call Other\t" + m + "\t" + obj.length);
   }
}
