package org.lushlife.negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mix-inされるメソッドに付加するアノテーション {@link Mixined}が付加されたメソッドは、 第一引数は、オーナークラスとなる。
 * 第二引数以降は、ハンドリング対象のメソッドに合わせて実装する。
 * 
 * {@link MethodMissing}と併用することも可能
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mixined
{

}
