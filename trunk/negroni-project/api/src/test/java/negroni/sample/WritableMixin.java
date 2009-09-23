package negroni.sample;

import java.io.Serializable;

import org.lushlife.negroni.annotation.Mixined;


public class WritableMixin
{
   @Mixined
   public void write(Serializable a, String string)
   {
      System.out.println("write Serializable " + a + " : " + string);
   }

   @Mixined
   public void write(Object a, String string)
   {
      System.out.println("write Object " + a + " : " + string);

   }
}