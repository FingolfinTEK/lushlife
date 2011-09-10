package pz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositRoop extends Roop {
	Function[] functions;
	int rank = -1;

	public CompositRoop(byte[] points, Function[] functions) {
		this.functions = functions;
		this.points = points;
		initPoint(points);
	}

	@Override
	public void f(Board board) {
		byte[] cp = null;
		if (DEBUG) {
			cp = new byte[board.b.length];
			System.arraycopy(board.b, 0, cp, 0, cp.length);
			f(cp);
		}
		for (Function f : functions) {
			f.f(board);
		}
		if (DEBUG) {
			String ex = toString(cp);
			String ac = toString(board.b);
			if (!ex.equals(ac)) {
				System.out.println(ex);
				System.out.println(ac);
				throw new IllegalStateException(this.toString());
			}
		}
	}

	@Override
	public void f_1(Board board) {
		byte[] cp = new byte[board.b.length];
		System.arraycopy(board.b, 0, cp, 0, cp.length);
		f_1(cp);

		for (int i = functions.length - 1; i >= 0; i--) {
			functions[i].f_1().f(board);
		}

		String ex = toString(cp);
		String ac = toString(board.b);
		if (!ex.equals(ac)) {
			System.out.println(ex);
			System.out.println(ac);
			throw new IllegalStateException(this.toString());
		}
	}

	public String toString() {
		return rank() + ":" + Arrays.toString(points) + ":"
				+ Arrays.toString(functions);
	}

	@Override
	public int rank() {
		if (rank == -1) {
			int rank = 0;
			for (Function f : functions) {
				rank += f.rank();
			}
			this.rank = rank;
		}
		return rank;
	}

	@Override
	public List<BaseRoop> base() {
		List<BaseRoop> list = new ArrayList<BaseRoop>();
		for (Function f : functions) {
			list.addAll(f.base());
		}
		return list;
	}

}
