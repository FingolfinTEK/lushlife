package negroni;

import java.sql.SQLException;


import org.junit.Test;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Mix;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.annotation.Mixined;
import org.lushlife.negroni.core.exceptions.ConfigurationException;
import org.lushlife.negroni.core.exceptions.NegroniException;

public class MixinErrorTest
{

   static public class TestRuntimeException extends RuntimeException
   {

   }

   static public class TestException extends Exception
   {

   }

   static public abstract class ExceptionTestClass implements MixinException
   {

   }

   @MixinImplementedBy(MixinExceptionImpl.class)
   static public interface MixinException
   {
      public void invoke(Class<? extends Exception> clazz) throws TestException;
   }

   static public class MixinExceptionImpl
   {
      @Mixined
      public void invoke(Object obj, Class<? extends Exception> clazz) throws Exception
      {
         throw clazz.newInstance();
      }
   }

   static public abstract class ExceptionTestClass2 implements MixinException2
   {

   }

   @MixinImplementedBy(MixinExceptionImpl2.class)
   static public interface MixinException2
   {
      public void invoke(Class<? extends Exception> clazz) throws TestException;
   }

   static public class MixinExceptionImpl2
   {
   }

   @Test(expected = NullPointerException.class)
   public void a01() throws TestException
   {
      ExceptionTestClass ex = Negroni.config().add(ExceptionTestClass.class).create().getInstance(ExceptionTestClass.class);
      ex.invoke(NullPointerException.class);
   }

   @Test(expected = TestException.class)
   public void a02() throws TestException
   {
      ExceptionTestClass ex = Negroni.config().add(ExceptionTestClass.class).create().getInstance(ExceptionTestClass.class);
      ex.invoke(TestException.class);
   }

   @Test(expected = NegroniException.class)
   public void a03() throws TestException
   {
      ExceptionTestClass ex = Negroni.config().add(ExceptionTestClass.class).create().getInstance(ExceptionTestClass.class);
      ex.invoke(SQLException.class);
   }

   @Test(expected = ConfigurationException.class)
   public void b01() throws Exception
   {
      Negroni.config().add(ExceptionTestClass2.class).create().getInstance(ExceptionTestClass2.class);
   }

   @Test(expected = NullPointerException.class)
   public void c01() throws TestException
   {
      Enhancer c = Negroni.create();
      MixinException ex = c.mixin(new Mix<MixinException>("test")
      {
      });
      ex.invoke(NullPointerException.class);
   }

   @Test(expected = TestException.class)
   public void c02() throws TestException
   {
      Enhancer c = Negroni.create();
      MixinException ex = c.mixin(new Mix<MixinException>("test")
      {
      });
      ex.invoke(TestException.class);
   }

   @Test(expected = NegroniException.class)
   public void c03() throws TestException
   {
      Enhancer c = Negroni.create();
      MixinException ex = c.mixin(new Mix<MixinException>("test")
      {
      });
      ex.invoke(SQLException.class);
   }

   @Test(expected = ConfigurationException.class)
   public void d01() throws TestException
   {
      Enhancer c = Negroni.create();
      c.mixin(new Mix<MixinException2>("test")
      {
      });
   }
}
