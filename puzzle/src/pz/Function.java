package pz;

import java.util.Collection;

public interface Function {
	void f(Board b);

	Function f_1();

	int rank();

	Collection<? extends BaseRoop> base();
}
