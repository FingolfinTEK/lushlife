package negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import negroni.core.exceptions.NegroniException;


/**
 * mix-in �C���^�t�F�[�X�̃��\�b�h�̓��AOverwrite���Ȃ���΂Ȃ�Ȃ����\�b�h�������A�m�e�[�V����
 * 
 * �������Ă��Ȃ������ꍇ�A����������{@link NegroniException}����������
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MustImplement {

}
