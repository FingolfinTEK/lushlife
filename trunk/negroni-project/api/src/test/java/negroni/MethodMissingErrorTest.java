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
			@interface MethodMissingでエラーが発生 {
				@interface RuntimeException {
				}

				@interface 呼び出しもとのメソッドのthrowsに含まれる例外 {
				}

				@interface 呼び出しもとのメソッドのthrowsに含まれない例外 {
				}
			}

			@interface ハンドリングのメソッドが見つからない {
			}
		}

		@interface Actions {
			@interface そのまま例外がスローされること {
			}

			@interface LatteInvocationTargetExceptionにラッピングされること {
			}

			@interface LatteConfigurationExceptionが発生すること {
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
	@latte.Conditions.MethodMissingでエラーが発生.RuntimeException
	@latte.Actions.そのまま例外がスローされること
	@Test(expected = TestRuntimeException.class)
	public void a01() throws TestException {
		testMissingError.invoke(TestRuntimeException.class);
	}

	@latte
	@latte.Conditions.MethodMissingでエラーが発生.呼び出しもとのメソッドのthrowsに含まれる例外
	@latte.Actions.そのまま例外がスローされること
	@Test(expected = TestException.class)
	public void a02() throws TestException {
		testMissingError.invoke(TestException.class);
	}

	@latte
	@latte.Conditions.MethodMissingでエラーが発生.呼び出しもとのメソッドのthrowsに含まれない例外
	@latte.Actions.LatteInvocationTargetExceptionにラッピングされること
	@Test(expected = RuntimeInvocationTargetException.class)
	public void a03() throws TestException {
		testMissingError.invoke(SQLException.class);
	}

	@latte
	@latte.Conditions.ハンドリングのメソッドが見つからない
	@latte.Actions.LatteConfigurationExceptionが発生すること
	@Test(expected = ConfigurationException.class)
	public void a04() {
		Negroni.config().add(TestMissingError2.class).create().getInstance(
				TestMissingError2.class);
	}
}
