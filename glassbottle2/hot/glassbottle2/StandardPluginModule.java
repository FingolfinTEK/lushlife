package glassbottle2;

import glassbottle2.CoreModule;
import glassbottle2.BeanBinder;
import glassbottle2.BeanModule;
import glassbottle2.context.ContextModule;

public class StandardPluginModule implements BeanModule
{

   @Override
   public void configure(BeanBinder binder)
   {
      binder.install(new CoreModule());
      binder.install(new ContextModule());
   }

   enum MSG
   {
      A,

      B
      {

      }
   }

   public static void main(String[] args)
   {
      System.out.println(MSG.A.getClass());
      System.out.println(MSG.B.getClass());
      System.out.println(MSG.B.getDeclaringClass());
   }

}
