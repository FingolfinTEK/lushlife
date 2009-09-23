package negroni.sample;

import java.lang.reflect.Method;

import org.lushlife.negroni.annotation.MethodMissing;
import org.lushlife.negroni.annotation.Mixined;


public class AMixinImpl
{

   @Mixined
   @MethodMissing
   public void method_missing(A a, Method m, Object... obj)
   {
      System.out.println(a + "\t" + m + "\t" + obj.length);
   }

}
