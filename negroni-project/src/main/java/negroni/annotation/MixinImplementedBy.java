package negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mix-inの実装クラスとの紐付けを行うアノテーション
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinImplementedBy
{
   /**
    * mix-inの実装クラス
    * 
    * @return
    */
   Class<?>[] value();
}
