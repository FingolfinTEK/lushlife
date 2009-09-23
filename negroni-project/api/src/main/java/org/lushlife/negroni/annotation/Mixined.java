package org.lushlife.negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mix-in����郁�\�b�h�ɕt������A�m�e�[�V���� {@link Mixined}���t�����ꂽ���\�b�h�́A �������́A�I�[�i�[�N���X�ƂȂ�B
 * �������ȍ~�́A�n���h�����O�Ώۂ̃��\�b�h�ɍ��킹�Ď�������B
 * 
 * {@link MethodMissing}�ƕ��p���邱�Ƃ��\
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mixined
{

}
