package negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���łɎ�������Ă��郁�\�b�h�𖳌��ɂ���
 * 
 * ���̃A�m�e�[�V�������t�����ꂽ���\�b�h�́A{@link MethodMissing}�܂���{@link Mixined}��
 * �n���h�����O�ΏۂƂȂ�B
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Undefined
{

}
