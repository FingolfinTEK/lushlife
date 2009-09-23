package negroni.sample;

import java.lang.reflect.Method;

import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.annotation.MethodMissing;



public abstract class MMTest {

	public abstract void invoke(String s);

	@MethodMissing
	public void handle(Method m, String s) {
		System.out.println("String " + s);
	}

	public abstract void invoke(int s);

	@MethodMissing
	public void handle(Method m, int s) {
		System.out.println("int " + s);
	}

	public static void main(String[] args) {
		MMTest test = Negroni.config().add(MMTest.class).create().getInstance(
				MMTest.class);
		test.invoke("test");
		test.invoke(100);
	}
}
