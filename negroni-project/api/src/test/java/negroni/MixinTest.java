package negroni;

import java.lang.annotation.Documented;

import junit.framework.Assert;


import org.junit.Test;
import org.lushlife.negroni.$;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.annotation.InstanceScoped;
import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.annotation.Mixined;
import org.lushlife.negroni.core.mixin.GetObjectMixin;

public class MixinTest
{

   @MixinImplementedBy(MixinImplement.class)
   static public interface MixTest extends GetObjectMixin
   {
      public void invoke();

      public void invoke(String s);

      public void invoke(Long s);

      public void invoke(long s);

      public void invoke(int s);

      public void invoke(char s);

      public Class<?> getCallParamType();

      public Class<?> getParamType();

   }

   @InstanceScoped
   static public class MixinImplement
   {
      Class<?> callMethod;

      @Mixined
      public Class<?> getCallParamType(Object obj)
      {
         return callMethod;
      }

      Class<?> paramType;

      @Mixined
      public Class<?> getParamType(Object obj)
      {
         return paramType;
      }

      @Mixined
      public void invoke(Long val)
      {
         callMethod = Long.class;
      }

      @Mixined
      public void invoke(Long val, Object obj)
      {
         callMethod = Number.class;
         paramType = Object.class;
      }

      @Mixined
      public void invoke(Long val, Number obj)
      {
         callMethod = Number.class;
         paramType = Number.class;
      }

      @Mixined
      public void invoke(Long val, Long obj)
      {
         callMethod = Number.class;
         paramType = Long.class;
      }

      @Mixined
      public void invoke(Long val, String obj)
      {
         callMethod = Number.class;
         paramType = String.class;
      }

      @Mixined
      public void invoke(Number val)
      {
         callMethod = Number.class;
      }

      @Mixined
      public void invoke(Number val, Object obj)
      {
         callMethod = Number.class;
         paramType = Object.class;
      }

      @Mixined
      public void invoke(Number val, Number obj)
      {
         callMethod = Number.class;
         paramType = Number.class;
      }

      @Mixined
      public void invoke(Number val, Long obj)
      {
         callMethod = Number.class;
         paramType = Long.class;
      }

      @Mixined
      public void invoke(Number val, String obj)
      {
         callMethod = Number.class;
         paramType = String.class;
      }

      @Mixined
      public void invoke(String val)
      {
         callMethod = String.class;
      }

      @Mixined
      public void invoke(String val, Object obj)
      {
         callMethod = String.class;
         paramType = Object.class;
      }

      @Mixined
      public void invoke(String val, Number obj)
      {
         callMethod = String.class;
         paramType = Number.class;
      }

      @Mixined
      public void invoke(String val, Long obj)
      {
         callMethod = String.class;
         paramType = Long.class;
      }

      @Mixined
      public void invoke(String val, String obj)
      {
         callMethod = String.class;
         paramType = String.class;
      }

      @Mixined
      public void invoke(Object val)
      {
         callMethod = Object.class;
      }

      @Mixined
      public void invoke(Object val, Object obj)
      {
         callMethod = Object.class;
         paramType = Object.class;
      }

      @Mixined
      public void invoke(Object val, Number obj)
      {
         callMethod = Object.class;
         paramType = Number.class;
      }

      @Mixined
      public void invoke(Object val, Long obj)
      {
         callMethod = Object.class;
         paramType = Long.class;
      }

      @Mixined
      public void invoke(Object val, String obj)
      {
         callMethod = Object.class;
         paramType = String.class;
      }
   }

   @Test
   public void a01()
   {
      Enhancer container = Negroni.create();
      MixTest mix = container.mixin(new $<MixTest>(10L)
      {
      });
      mix.invoke();
      Assert.assertEquals(Long.class, mix.getCallParamType());
   }

   @Test
   public void a02()
   {
      Enhancer container = Negroni.create();
      MixTest mix = container.mixin(new $<MixTest>("10")
      {
      });
      Assert.assertEquals("10", mix.getMixinObject());

      mix.invoke();
      Assert.assertEquals(String.class, mix.getCallParamType());

      mix.invoke('c');
      Assert.assertEquals(String.class, mix.getCallParamType());
      Assert.assertEquals(Object.class, mix.getParamType());

      mix.invoke(10);
      Assert.assertEquals(String.class, mix.getCallParamType());
      Assert.assertEquals(Number.class, mix.getParamType());

      mix.invoke(10L);
      Assert.assertEquals(String.class, mix.getCallParamType());
      Assert.assertEquals(Long.class, mix.getParamType());

      mix.invoke(new Long(10L));
      Assert.assertEquals(String.class, mix.getCallParamType());
      Assert.assertEquals(Long.class, mix.getParamType());

      mix.invoke("String");
      Assert.assertEquals(String.class, mix.getCallParamType());
      Assert.assertEquals(String.class, mix.getParamType());

   }

   @Test
   public void a03()
   {
      Enhancer container = Negroni.create();
      MixTest mix = container.mixin(new $<MixTest>(10)
      {
      });
      Assert.assertEquals(10, mix.getMixinObject());

      mix.invoke();
      Assert.assertEquals(Number.class, mix.getCallParamType());

      mix.invoke('c');
      Assert.assertEquals(Number.class, mix.getCallParamType());
      Assert.assertEquals(Object.class, mix.getParamType());

      mix.invoke(10);
      Assert.assertEquals(Number.class, mix.getCallParamType());
      Assert.assertEquals(Number.class, mix.getParamType());

      mix.invoke(10L);
      Assert.assertEquals(Number.class, mix.getCallParamType());
      Assert.assertEquals(Long.class, mix.getParamType());

      mix.invoke(new Long(10L));
      Assert.assertEquals(Number.class, mix.getCallParamType());
      Assert.assertEquals(Long.class, mix.getParamType());

      mix.invoke("String");
      Assert.assertEquals(Number.class, mix.getCallParamType());
      Assert.assertEquals(String.class, mix.getParamType());
   }

   @Test
   public void a04()
   {
      Enhancer container = Negroni.create();
      MixTest mix = container.mixin(new $<MixTest>('a')
      {
      });
      Assert.assertEquals('a', mix.getMixinObject());

      mix.invoke();
      Assert.assertEquals(Object.class, mix.getCallParamType());

      mix.invoke('c');
      Assert.assertEquals(Object.class, mix.getCallParamType());
      Assert.assertEquals(Object.class, mix.getParamType());

      mix.invoke(10);
      Assert.assertEquals(Object.class, mix.getCallParamType());
      Assert.assertEquals(Number.class, mix.getParamType());

      mix.invoke(10L);
      Assert.assertEquals(Object.class, mix.getCallParamType());
      Assert.assertEquals(Long.class, mix.getParamType());

      mix.invoke(new Long(10L));
      Assert.assertEquals(Object.class, mix.getCallParamType());
      Assert.assertEquals(Long.class, mix.getParamType());

      mix.invoke("String");
      Assert.assertEquals(Object.class, mix.getCallParamType());
      Assert.assertEquals(String.class, mix.getParamType());
   }
}
