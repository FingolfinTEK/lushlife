package negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * すでに実装されているメソッドを無効にする
 * 
 * このアノテーションが付加されたメソッドは、{@link MethodMissing}または{@link Mixined}の
 * ハンドリング対象となる。
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Undefined
{

}
