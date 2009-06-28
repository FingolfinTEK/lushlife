package negroni;

import java.lang.annotation.Documented;
import java.lang.reflect.Method;

import junit.framework.Assert;

import negroni.annotation.MethodMissing;
import negroni.annotation.Undefined;

import org.junit.Before;
import org.junit.Test;


public class MethodMissingTest {

	@Documented
	private @interface latte {
		@interface Conditions {
			@interface ��`����Ă���֐����Ăяo�� {
			}

			@interface ��`����Ă��Ȃ��֐����Ăяo�� {
			}

			@interface ���� {
				@interface String {
				}

				@interface Integer {
				}

				@interface Primitive_int {
				}

				@interface Long {
				}

				@interface ���̑� {
				}
			}

			@interface Undefine���A�m�e�[�V�����t���Ă��� {
			}
		}

		@interface Actions {
			@interface �{���̃��\�b�h���Ăяo����� {
			}

			@interface callMissingMethod {
				@interface Param_String {
				}

				@interface Param_Integer {
				}

				@interface Param_Number {
				}

				@interface Param_Object {
				}

			}

			@interface �D�揇�ʂ̍���MethodMissing���Ă΂�邱�� {
			}
		}
	}

	static abstract public class TestMissing {
		public abstract void invoke(int value);

		public abstract void invoke(long value);

		public abstract void invoke(Integer value);

		public abstract void invoke(String value);

		public abstract void invoke(Object obj);

		Class callMethod;

		public Class getCallMethod() {
			return callMethod;
		}

		@MethodMissing
		public void missing(Method m, Integer i) {
			this.callMethod = Integer.class;
		}

		@MethodMissing
		public void missing(Method m, Number i) {
			this.callMethod = Number.class;
		}

		@MethodMissing
		public void missing(Method m, String i) {
			this.callMethod = String.class;
		}

		@MethodMissing
		public void missing(Method m, Object i) {
			this.callMethod = Object.class;
		}

		public void invoke(byte b) {
			this.callMethod = Byte.class;
		}

		@Undefined
		public void invoke(short b) {
			this.callMethod = Short.class;
		}
	}

	@Before
	public void init() {
		testMissing = Negroni.config().add(TestMissing.class).create()
				.getInstance(TestMissing.class);
	}

	TestMissing testMissing;

	@latte
	@latte.Conditions.��`����Ă���֐����Ăяo��
	@latte.Actions.�{���̃��\�b�h���Ăяo�����
	@Test
	public void a01() throws Exception {
		testMissing.invoke((byte) 10);
		Assert.assertEquals(Byte.class, testMissing.getCallMethod());
	}

	@latte
	@latte.Conditions.Undefine���A�m�e�[�V�����t���Ă���
	@latte.Conditions.��`����Ă���֐����Ăяo��
	@latte.Actions.callMissingMethod
	@Test
	public void a02() throws Exception {
		testMissing.invoke((short) 10);
		Assert.assertEquals(Number.class, testMissing.getCallMethod());
	}

	@latte
	@latte.Actions.callMissingMethod.Param_String
	@latte.Conditions.����.String
	@latte.Conditions.��`����Ă��Ȃ��֐����Ăяo��
	@Test
	public void b01() throws Exception {
		testMissing.invoke("String");
		Assert.assertEquals(String.class, testMissing.getCallMethod());
	}

	@latte
	@latte.Conditions.��`����Ă��Ȃ��֐����Ăяo��
	@latte.Conditions.����.Integer
	@latte.Actions.callMissingMethod.Param_Integer
	@Test
	public void b02() throws Exception {
		testMissing.invoke(10);
		Assert.assertEquals(Integer.class, testMissing.getCallMethod());
	}

	@latte
	@latte.Conditions.��`����Ă��Ȃ��֐����Ăяo��
	@latte.Conditions.����.Primitive_int
	@latte.Actions.callMissingMethod.Param_Integer
	@latte.Actions.�D�揇�ʂ̍���MethodMissing���Ă΂�邱��
	@Test
	public void b03() throws Exception {
		testMissing.invoke(new Integer(100));
		Assert.assertEquals(Integer.class, testMissing.getCallMethod());
	}

	@latte
	@latte.Actions.callMissingMethod.Param_Number
	@latte.Conditions.��`����Ă��Ȃ��֐����Ăяo��
	@latte.Conditions.����.Long
	@Test
	public void b04() throws Exception {
		testMissing.invoke(100L);
		Assert.assertEquals(Number.class, testMissing.getCallMethod());
	}

	@latte
	@latte.Actions.callMissingMethod.Param_Object
	@latte.Conditions.��`����Ă��Ȃ��֐����Ăяo��
	@latte.Conditions.����.���̑�
	@Test
	public void b05() throws Exception {
		testMissing.invoke(new StringBuilder());
		Assert.assertEquals(Object.class, testMissing.getCallMethod());
	}

}
