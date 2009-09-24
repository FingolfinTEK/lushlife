package org.lushlife.negroni;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * 実装されていないメソッドを呼び出した場合にハンドリングするメソッド付加する。 第一引数は必ず{@link Method}を引数に取る。
 * 第二引数以降は、ハンドリング対象のメソッドに合わせて実装する。
 * 
 * 
 * {@link MixinMethod}と共に用いる場合には、 第一引数はオーナークラス、 第二引数は{@link Method}
 * を引数に取る。第三引数以降は、ハンドリング対象のメソッドに合わせて実装する。
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMissing {

}
