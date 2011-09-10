package pz;

import java.util.Collection;
import java.util.List;

public interface Function {
	void f(Board b);

	Function f_1();

	int rank();

	Collection<? extends BaseRoop> base();

	List<Operation> operations();
}
