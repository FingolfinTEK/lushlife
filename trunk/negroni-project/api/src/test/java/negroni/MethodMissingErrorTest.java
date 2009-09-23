package negroni;

import java.lang.annotation.Documented;
import java.lang.reflect.Method;
import java.sql.SQLException;


import org.junit.Before;
import org.junit.Test;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.annotation.MethodMissing;
import org.lushlife.negroni.core.exceptions.ConfigurationException;
import org.lushlife.negroni.core.exceptions.RuntimeInvocationTargetException;


public class MethodMissingErrorTest {

	@Documented
	private @interface latte {
		@interface Conditions {
			@interface MethodMissing�ŃG���[������ {
				@interface RuntimeException {
				}

				@interface �Ăяo�����Ƃ̃��\�b�h��throws�Ɋ܂܂���O {
				}

				@interface �Ăяo�����Ƃ̃��\�b�h��throws�Ɋ܂܂�Ȃ���O {
				}
			}

			@interface �n���h�����O�̃��\�b�h��������Ȃ� {
			}
		}

		@interface Actions {
			@interface ���̂܂ܗ�O���X���[����邱�� {
			}

			@interface LatteInvocationTargetException�Ƀ��b�s���O����邱�� {
			}

			@interface LatteConfigurationException���������邱�� {
			}
		}
	}

	static public class TestException extends Exception {
	}

	static public class TestRuntimeException extends RuntimeException {
	}

	static abstract public class TestMissingError {
		abstract public void invoke(Class<? extends Exception> clazz)
				throws TestException;

		@MethodMissing
		public void missing(Method m, Class<? extends Exception> clazz)
				throws Exception {
			throw clazz.newInstance();
		}
	}

	static abstract public class TestMissingError2 {
		abstract public void invoke(Class<? extends Exception> clazz)
				throws TestException;
	}

	@Before
	public void init() {
		testMissingError = Negroni.config().add(TestMissingError.class).create()
				.getInstance(TestMissingError.class);
	}

	TestMissingError testMissingError;

	@latte
	@latte.Conditions.MethodMissing�ŃG���[������.RuntimeException
	@latte.Actions.���̂܂ܗ�O���X���[����邱��
	@Test(expected = TestRuntimeException.class)
	public void a01() throws TestException {
		testMissingError.invoke(TestRuntimeException.class);
	}

	@latte
	@latte.Conditions.MethodMissing�ŃG���[������.�Ăяo�����Ƃ̃��\�b�h��throws�Ɋ܂܂���O
	@latte.Actions.���̂܂ܗ�O���X���[����邱��
	@Test(expected = TestException.class)
	public void a02() throws TestException {
		testMissingError.invoke(TestException.class);
	}

	@latte
	@latte.Conditions.MethodMissing�ŃG���[������.�Ăяo�����Ƃ̃��\�b�h��throws�Ɋ܂܂�Ȃ���O
	@latte.Actions.LatteInvocationTargetException�Ƀ��b�s���O����邱��
	@Test(expected = RuntimeInvocationTargetException.class)
	public void a03() throws TestException {
		testMissingError.invoke(SQLException.class);
	}

	@latte
	@latte.Conditions.�n���h�����O�̃��\�b�h��������Ȃ�
	@latte.Actions.LatteConfigurationException���������邱��
	@Test(expected = ConfigurationException.class)
	public void a04() {
		Negroni.config().add(TestMissingError2.class).create().getInstance(
				TestMissingError2.class);
	}
}
