package org.lushlife.negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * ��������Ă��Ȃ����\�b�h���Ăяo�����ꍇ�Ƀn���h�����O���郁�\�b�h�t������B �������͕K��{@link Method}�������Ɏ��B
 * �������ȍ~�́A�n���h�����O�Ώۂ̃��\�b�h�ɍ��킹�Ď�������B
 * 
 * 
 * {@link Mixined}�Ƌ��ɗp����ꍇ�ɂ́A �������̓I�[�i�[�N���X�A ��������{@link Method}
 * �������Ɏ��B��O�����ȍ~�́A�n���h�����O�Ώۂ̃��\�b�h�ɍ��킹�Ď�������B
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMissing
{

}
