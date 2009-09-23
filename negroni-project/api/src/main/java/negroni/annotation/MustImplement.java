package negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import negroni.core.exceptions.NegroniException;


/**
 * mix-in インタフェースのメソッドの内、Overwriteしなければならないメソッドを示すアノテーション
 * 
 * 実装していなかった場合、初期化中に{@link NegroniException}が発生する
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MustImplement {

}
