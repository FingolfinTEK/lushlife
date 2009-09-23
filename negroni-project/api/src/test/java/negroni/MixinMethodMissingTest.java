package negroni;

import java.lang.annotation.Documented;
import java.lang.reflect.Method;

import junit.framework.Assert;


import org.junit.Test;
import org.lushlife.negroni.$;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.annotation.InstanceScoped;
import org.lushlife.negroni.annotation.MethodMissing;
import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.annotation.Mixined;
import org.lushlife.negroni.core.mixin.GetObjectMixin;

public class MixinMethodMissingTest
{

   @MixinImplementedBy(MethodMissingMixImple.class)
   static public interface MethodMissingMix extends GetObjectMixin
   {
      void invoke();

      void invoke(int value);

      void invoke(long value);

      void invoke(Long value);

      void invoke(String value);

      void invoke(Object obj);

      Class<?> getOwnerClass();

      Class<?> getParamClass();

   }

   @InstanceScoped
   static public class MethodMissingMixImple
   {

      Class<?> ownerClass;

      Class<?> paramClass;

      @Mixined
      public Class<?> getOwnerClass(Object obj)
      {
         return ownerClass;
      }

      @Mixined
      public Class<?> getParamClass(Object obj)
      {
         return paramClass;
      }

      @Mixined
      @MethodMissing
      public void missing(String value, Method mehtod)
      {
         ownerClass = String.class;
      }

      @Mixined
      @MethodMissing
      public void missing(String value, Method mehtod, Object obj)
      {
         ownerClass = String.class;
         paramClass = Object.class;
      }

      @Mixined
      @MethodMissing
      public void missing(String value, Method mehtod, String obj)
      {
         ownerClass = String.class;
         paramClass = String.class;
      }

      @Mixined
      @MethodMissing
      public void missing(String value, Method mehtod, Long obj)
      {
         ownerClass = String.class;
         paramClass = Long.class;
      }

      @Mixined
      @MethodMissing
      public void missing(String value, Method mehtod, Number obj)
      {
         ownerClass = String.class;
         paramClass = Number.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Number value, Method mehtod)
      {
         ownerClass = Number.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Number value, Method mehtod, Object obj)
      {
         ownerClass = Number.class;
         paramClass = Object.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Number value, Method mehtod, String obj)
      {
         ownerClass = Number.class;
         paramClass = String.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Number value, Method mehtod, Long obj)
      {
         ownerClass = Number.class;
         paramClass = Long.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Number value, Method mehtod, Number obj)
      {
         ownerClass = Number.class;
         paramClass = Number.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Long value, Method mehtod)
      {
         ownerClass = Long.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Long value, Method mehtod, Object obj)
      {
         ownerClass = Long.class;
         paramClass = Object.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Long value, Method mehtod, String obj)
      {
         ownerClass = Long.class;
         paramClass = String.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Long value, Method mehtod, Long obj)
      {
         ownerClass = Long.class;
         paramClass = Long.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Long value, Method mehtod, Number obj)
      {
         ownerClass = Long.class;
         paramClass = Number.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Object value, Method mehtod)
      {
         ownerClass = Object.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Object value, Method mehtod, Object obj)
      {
         ownerClass = Object.class;
         paramClass = Object.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Object value, Method mehtod, String obj)
      {
         ownerClass = Object.class;
         paramClass = String.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Object value, Method mehtod, Long obj)
      {
         ownerClass = Object.class;
         paramClass = Long.class;
      }

      @Mixined
      @MethodMissing
      public void missing(Object value, Method mehtod, Number obj)
      {
         ownerClass = Object.class;
         paramClass = Number.class;
      }

   }

   @Test
   public void a01() throws Exception
   {
      MethodMissingMix mixin = Negroni.config().add(MethodMissingMix.class).create().getInstance(MethodMissingMix.class);
      mixin.invoke();
      Assert.assertEquals(Object.class, mixin.getOwnerClass());
   }

   @Test
   public void a02() throws Exception
   {
      Enhancer container = Negroni.create();
      MethodMissingMix mix = container.mixin(new $<MethodMissingMix>("String")
      {
      });
      mix.invoke();
      Assert.assertEquals(String.class, mix.getOwnerClass());
   }

   @Test
   public void a03() throws Exception
   {
      Enhancer container = Negroni.create();
      MethodMissingMix mix = container.mixin(new $<MethodMissingMix>('c')
      {
      });
      mix.invoke();
      Assert.assertEquals(Object.class, mix.getOwnerClass());

      mix.invoke(new Object());
      Assert.assertEquals(Object.class, mix.getOwnerClass());
      Assert.assertEquals(Object.class, mix.getParamClass());

      mix.invoke(10);
      Assert.assertEquals(Object.class, mix.getOwnerClass());
      Assert.assertEquals(Number.class, mix.getParamClass());

      mix.invoke(10L);
      Assert.assertEquals(Object.class, mix.getOwnerClass());
      Assert.assertEquals(Long.class, mix.getParamClass());

      mix.invoke(new Long(10));
      Assert.assertEquals(Object.class, mix.getOwnerClass());
      Assert.assertEquals(Long.class, mix.getParamClass());

      mix.invoke("String");
      Assert.assertEquals(Object.class, mix.getOwnerClass());
      Assert.assertEquals(String.class, mix.getParamClass());

   }

   @Test
   public void a04() throws Exception
   {
      Enhancer container = Negroni.create();
      MethodMissingMix mix = container.mixin(new $<MethodMissingMix>(10L)
      {
      });
      mix.invoke();
      Assert.assertEquals(Long.class, mix.getOwnerClass());

      mix.invoke(new Object());
      Assert.assertEquals(Long.class, mix.getOwnerClass());
      Assert.assertEquals(Object.class, mix.getParamClass());

      mix.invoke(10);
      Assert.assertEquals(Long.class, mix.getOwnerClass());
      Assert.assertEquals(Number.class, mix.getParamClass());

      mix.invoke(10L);
      Assert.assertEquals(Long.class, mix.getOwnerClass());
      Assert.assertEquals(Long.class, mix.getParamClass());

      mix.invoke(new Long(10));
      Assert.assertEquals(Long.class, mix.getOwnerClass());
      Assert.assertEquals(Long.class, mix.getParamClass());

      mix.invoke("String");
      Assert.assertEquals(Long.class, mix.getOwnerClass());
      Assert.assertEquals(String.class, mix.getParamClass());
   }

   @Test
   public void a05() throws Exception
   {
      Enhancer container = Negroni.create();
      MethodMissingMix mix = container.mixin(new $<MethodMissingMix>(10)
      {
      });
      mix.invoke();
      Assert.assertEquals(Number.class, mix.getOwnerClass());

      mix.invoke(new Object());
      Assert.assertEquals(Number.class, mix.getOwnerClass());
      Assert.assertEquals(Object.class, mix.getParamClass());

      mix.invoke(10);
      Assert.assertEquals(Number.class, mix.getOwnerClass());
      Assert.assertEquals(Number.class, mix.getParamClass());

      mix.invoke(10L);
      Assert.assertEquals(Number.class, mix.getOwnerClass());
      Assert.assertEquals(Long.class, mix.getParamClass());

      mix.invoke(new Long(10));
      Assert.assertEquals(Number.class, mix.getOwnerClass());
      Assert.assertEquals(Long.class, mix.getParamClass());

      mix.invoke("String");
      Assert.assertEquals(Number.class, mix.getOwnerClass());
      Assert.assertEquals(String.class, mix.getParamClass());
   }

}
