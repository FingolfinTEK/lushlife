package negroni.sample;

import org.lushlife.negroni.Negroni;

public abstract class ConcreteSample extends MethodMissingSample
{
   public abstract void invoke(String message);

   public abstract void invoke(int message);

   public static void main(String[] args)
   {
      org.lushlife.negroni.Enhancer enhancer = Negroni.config().add(ConcreteSample.class).create();
      ConcreteSample sample = enhancer.getInstance(ConcreteSample.class);
      sample.invoke("invoke");
      sample.invoke(10);
   }

}
