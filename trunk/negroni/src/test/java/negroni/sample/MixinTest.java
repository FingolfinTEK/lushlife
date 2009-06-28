package negroni.sample;

import negroni.Enhancer;
import negroni.Negroni;

import org.junit.Test;

public class MixinTest
{

   @Test
   public void a01()
   {
      Enhancer enhancer = Negroni.config().add(A.class).add(B.class).create();
      A a = enhancer.getInstance(A.class);
      a.write("write");

      B b = enhancer.getInstance(B.class);
      b.write("write");
   }

}
