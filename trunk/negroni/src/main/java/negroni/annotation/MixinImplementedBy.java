package negroni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mix-in�̎����N���X�Ƃ̕R�t�����s���A�m�e�[�V����
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinImplementedBy
{
   /**
    * mix-in�̎����N���X
    * 
    * @return
    */
   Class<?>[] value();
}
