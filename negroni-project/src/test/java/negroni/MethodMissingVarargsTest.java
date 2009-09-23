package negroni;

import java.lang.annotation.Documented;
import java.lang.reflect.Method;

import junit.framework.Assert;

import negroni.annotation.MethodMissing;
import negroni.annotation.Undefined;

import org.junit.Before;
import org.junit.Test;

public class MethodMissingVarargsTest
{

   static abstract public class TestMissing
   {
      public abstract void invoke(int value);

      public abstract void invoke(long value);

      public abstract void invoke(Integer value);

      public abstract void invoke(String value);

      public abstract void invoke(Object obj);

      //
      public abstract void invoke2(String value, int... v);

      public abstract void invoke2(long... value);

      //
      public abstract void invoke2(String... value);

      //
      public abstract void invoke2(Object... obj);

      Class callMethod;

      public Class getCallMethod()
      {
         return callMethod;
      }

      Class callMethod2;

      public Class getCallMethod2()
      {
         return callMethod2;
      }

      @MethodMissing
      public void missing(Method m, String s, Integer... i)
      {
         Assert.assertEquals("String", s);
         Assert.assertEquals("10", i[0].toString());

         this.callMethod = String.class;
         this.callMethod2 = Integer.class;
      }

      @MethodMissing
      public void missing(Method m, Integer... i)
      {
         this.callMethod = Integer.class;
      }

      @MethodMissing
      public void missing(Method m, Number... i)
      {
         Assert.assertEquals("10", i[0].toString());
         this.callMethod = Number.class;
      }

      @MethodMissing
      public void missing(Method m, String... i)
      {
         this.callMethod = String.class;
      }

      @MethodMissing
      public void missing(Method m, Object... i)
      {
         this.callMethod = Object.class;
      }

      public void invoke(byte b)
      {
         this.callMethod = Byte.class;
      }

      @Undefined
      public void invoke(short b)
      {
         this.callMethod = Short.class;
      }
   }

   @Before
   public void init()
   {
      testMissing = Negroni.config().add(TestMissing.class).create().getInstance(TestMissing.class);
   }

   TestMissing testMissing;

   @Test
   public void a01() throws Exception
   {
      testMissing.invoke((byte) 10);
      Assert.assertEquals(Byte.class, testMissing.getCallMethod());
   }

   @Test
   public void a02() throws Exception
   {
      testMissing.invoke((short) 10);
      Assert.assertEquals(Number.class, testMissing.getCallMethod());
   }

   @Test(expected = Exception.class)
   public void b01() throws Exception
   {
      testMissing.invoke("String");
      Assert.assertEquals(String.class, testMissing.getCallMethod());
   }

   @Test
   public void b02() throws Exception
   {
      testMissing.invoke(10);
      Assert.assertEquals(Integer.class, testMissing.getCallMethod());
   }

   @Test
   public void b03() throws Exception
   {
      testMissing.invoke(new Integer(100));
      Assert.assertEquals(Integer.class, testMissing.getCallMethod());
   }

   @Test
   public void b04() throws Exception
   {
      testMissing.invoke(10L);
      Assert.assertEquals(Number.class, testMissing.getCallMethod());
   }

   @Test
   public void b05() throws Exception
   {
      testMissing.invoke(new StringBuilder());
      Assert.assertEquals(Object.class, testMissing.getCallMethod());
   }

   @Test
   public void c01() throws Exception
   {
      testMissing.invoke2("String", "String");
      Assert.assertEquals(String.class, testMissing.getCallMethod());
   }

   @Test
   public void c02() throws Exception
   {
      testMissing.invoke2(10);
      Assert.assertEquals(Number.class, testMissing.getCallMethod());
   }

   @Test
   public void c03() throws Exception
   {
      testMissing.invoke2(new Integer(10));
      Assert.assertEquals(Number.class, testMissing.getCallMethod());
   }

   @Test
   public void c04() throws Exception
   {
      testMissing.invoke2(10L);
      Assert.assertEquals(Number.class, testMissing.getCallMethod());
   }

   @Test
   public void c05() throws Exception
   {
      testMissing.invoke2(new StringBuilder());
      Assert.assertEquals(Object.class, testMissing.getCallMethod());
   }

   @Test
   public void c06() throws Exception
   {
      testMissing.invoke2("String", 10, 10);
      Assert.assertEquals(String.class, testMissing.getCallMethod());
      Assert.assertEquals(Integer.class, testMissing.getCallMethod2());
   }

}
