package pz;

public enum Operation {
	L, U, D, R;

	public int move(int target, Board board) {
		switch (this) {
		case L:
			return board.l(target);
		case R:
			return board.r(target);
		case U:
			return board.u(target);
		case D:
			return board.d(target);
		}
		return -1;
	}

	public Operation forward() {
		switch (this) {
		case L:
			return L;
		case R:
			return R;
		case U:
			return U;
		case D:
			return D;
		}
		return null;
	}

	public Operation right() {
		switch (this) {
		case L:
			return U;
		case R:
			return D;
		case U:
			return R;
		case D:
			return L;
		}
		return null;
	}

	public Operation[] search() {
		return new Operation[] { right(), forward(), left() };
	}

	private Operation left() {
		return right().f_1();
	}

	public Operation f_1() {
		switch (this) {
		case L:
			return R;
		case R:
			return L;
		case U:
			return D;
		case D:
			return U;
		}
		return null;
	}
}
