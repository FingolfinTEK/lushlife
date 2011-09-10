package pz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

abstract public class Roop implements Comparable<Roop> {
	boolean DEBUG = false;
	byte entry;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rank();
		result = prime * result + Arrays.hashCode(points);
		return result;
	}

	@Override
	public int compareTo(Roop arg0) {
		if (entry != arg0.entry) {
			return entry - arg0.entry;
		}
		if (rank() != arg0.rank()) {
			return rank() - arg0.rank();
		}
		if (points.length != arg0.points.length) {
			return points.length - arg0.points.length;
		}
		for (int i = 0; i < points.length; i++) {
			if (points[i] != arg0.points[i]) {
				return points[i] - arg0.points[i];
			}
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roop other = (Roop) obj;
		if (entry != other.entry) {
			return false;
		}
		if (rank() != other.rank()) {
			return false;
		}
		if (!Arrays.equals(points, other.points))
			return false;
		return true;
	}

	int current = 0;
	protected byte[] points;

	public Roop() {
		super();
	}

	abstract public int rank();

	public void f(byte[] b) {
		byte[] r = points;
		byte tmp = b[r[0]];
		for (int i = 0; i < r.length - 1; i++) {
			b[r[i]] = b[r[i + 1]];
		}
		b[r[r.length - 1]] = tmp;
	}

	public String toString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (byte bb : b) {
			sb.append((char) bb);
		}
		return sb.toString();
	}

	public void f_1(byte[] b) {
		byte[] r = points;
		byte tmp = b[r[r.length - 1]];
		for (int i = r.length - 1; i > 0; i--) {
			b[r[i]] = b[r[i - 1]];
		}
		b[r[0]] = tmp;
	}

	abstract public void f(Board board);

	abstract public void f_1(Board board);

	public Function f() {
		return new Function() {
			public void f(Board b) {
				Roop.this.f(b);
			}

			public Function f_1() {
				return Roop.this.f_1();
			}

			public String toString() {
				return "F(" + Roop.this + ")";
			}

			@Override
			public int rank() {
				return Roop.this.rank();
			}

			@Override
			public Collection<BaseRoop> base() {
				return Roop.this.base();
			}

			@Override
			public boolean equals(Object arg0) {
				if (arg0 instanceof Function) {
					Function f = (Function) arg0;
					return toString().equals(f.toString());
				}
				return false;
			}

			@Override
			public List<Operation> operations() {
				return Roop.this.operations();
			}

		};
	}

	public Function f_1() {
		return new Function() {

			public Function f_1() {
				return Roop.this.f();
			}

			public void f(Board b) {
				Roop.this.f_1(b);
			}

			public String toString() {
				return "F-1(" + Roop.this + ")";
			}

			@Override
			public int rank() {
				return Roop.this.rank();
			}

			@Override
			public Collection<BaseRoop> base() {
				return Roop.this.base();
			}

			@Override
			public boolean equals(Object arg0) {
				if (arg0 instanceof Function) {
					Function f = (Function) arg0;
					return toString().equals(f.toString());
				}
				return false;
			}

			@Override
			public List<Operation> operations() {
				ArrayList<Operation> operations = new ArrayList<Operation>();
				operations.addAll(Roop.this.operations());
				Collections.reverse(operations);
				return operations;
			}
		};
	}

	public SortedSet<Byte> and(Roop roop) {
		SortedSet<Byte> set = new TreeSet<Byte>();
		int i = 0;
		int j = 0;

		byte[] mc = new byte[this.points.length];
		System.arraycopy(this.points, 0, mc, 0, mc.length);
		Arrays.sort(mc);

		byte[] tc = new byte[roop.points.length];
		System.arraycopy(roop.points, 0, tc, 0, tc.length);
		Arrays.sort(tc);

		while (i < mc.length && j < tc.length) {
			if (mc[i] == tc[j]) {
				set.add(mc[i]);
				i++;
				j++;
				continue;
			}
			if (mc[i] < tc[j]) {
				i++;
				continue;
			}
			if (mc[i] > tc[j]) {
				j++;
				continue;
			}
		}
		return set;
	}

	public int p(int point) {
		for (int i = 0; i < points.length; i++) {
			if (points[i] == point) {
				int found = i - current;
				while (found < 0) {
					found += points.length;
				}
				return found;
			}
		}
		return -1;
	}

	public List<Function> move(int num, int to) {
		int from = p(num);
		if (from == -1) {
			throw new IllegalStateException(num + " " + to + " " + this);
		}
		int len = to - from;
		if (Math.abs(len) >= points.length / 2) {
			if (len < 0) {
				len += points.length;
			} else {
				len -= points.length;
			}
		}
		List<Function> list = new ArrayList<Function>();
		if (len < 0) {
			while (len != 0) {
				len++;
				current++;
				list.add(f());
			}
		} else {
			while (len != 0) {
				len--;
				current--;
				list.add(f_1());
			}
		}
		return list;
	}

	public CompositRoop createSubRoop(Roop roop, byte[] points) {
		Set<Byte> and = and(roop);
		if (and.size() != 1) {
			throw new IllegalStateException();
		}
		int crossPoint = and.iterator().next();
		int crossPointP = roop.p(crossPoint);
		List<Function> list = new ArrayList<Function>();
		for (byte point : points) {
			List<Function> moveList = roop.move(point, crossPointP);
			list.addAll(moveList);
			list.add(f());
		}
		List<Function> output = new ArrayList<Function>();
		output.addAll(list);
		output.add(f());

		for (int i = list.size() - 1; i >= 0; i--) {
			output.add(list.get(i).f_1());
		}
		return new CompositRoop(points, output.toArray(new Function[0]),
				roop.entry);
	}

	public CompositRoop baseChange(Roop target, byte to) {
		if (target.points.length != 3) {
			throw new IllegalStateException(this + " " + target + " " + to);
		}
		if (points.length != 3) {
			throw new IllegalStateException(this + " " + target + " " + to);
		}
		SortedSet<Byte> and = and(target);
		if (and.size() != 1) {
			throw new IllegalStateException("this " + this + "  target "
					+ target + " " + to);
		}
		int from = and.iterator().next();
		Function[] functions = new Function[3];
		int p = -1;
		for (int i = 0; i < 3; i++) {
			if (points[i] == from) {
				p = i;
				break;
			}
		}
		if (points[(p + 1) % 3] == to) {
			functions[0] = f();
		} else {
			functions[0] = f_1();
		}
		functions[1] = target.f();

		functions[2] = functions[0].f_1();
		byte[] newPoints = new byte[3];
		for (int i = 0; i < 3; i++) {
			if (target.points[i] == from) {
				newPoints[i] = to;
			} else {
				newPoints[i] = target.points[i];
			}
		}
		return new CompositRoop(newPoints, functions, target.entry);
	}

	public boolean constains(byte value) {
		for (byte b : points) {
			if (b == value) {
				return true;
			}
		}
		return false;
	}

	protected void initPoint(byte[] r) {
		byte min = r[0];
		for (int i = 1; i < r.length; i++) {
			if (min > r[i]) {
				min = r[i];
			}
		}
		while (min != r[0]) {
			byte tmp = r[0];
			for (int i = 0; i < r.length - 1; i++) {
				r[i] = r[i + 1];
			}
			r[r.length - 1] = tmp;
		}
	}

	public byte other(byte... values) {
		TreeSet<Byte> set = new TreeSet<Byte>();
		for (byte v : values) {
			set.add(v);
		}
		for (byte v : points) {
			if (!set.contains(v)) {
				return v;
			}
		}
		return -1;
	}

	abstract public List<BaseRoop> base();

	public String pointKey() {
		StringBuilder sb = new StringBuilder();
		for (byte point : points) {
			sb.append(point + ":");
		}
		return sb.toString();
	}

	public String transformString(Board b) {
		byte[] tmp = new byte[b.g.length];
		System.arraycopy(b.g, 0, tmp, 0, tmp.length);
		StringBuilder sb = new StringBuilder();
		f(tmp);

		for (int i = 0; i < tmp.length; i++) {
			if (entry == i) {
				sb.append("*");
			} else if (b.g[i] == tmp[i]) {
				sb.append("-");
			} else {
				sb.append((char) tmp[i]);
			}
		}
		return sb.toString();
	}

	public byte first() {
		return points[0];
	}

	public byte last() {
		return points[points.length - 1];
	}

	public byte[] join(Roop b) {
		if (last() != b.first()) {
			throw new IllegalStateException();
		}
		byte[] join = new byte[points.length + b.points.length - 1];
		System.arraycopy(points, 0, join, 0, points.length);
		System.arraycopy(b.points, 1, join, points.length, b.points.length - 1);
		return join;
	}

	public boolean isCross(Board b) {
		for (int i = 0; i < points.length - 1; i++) {
			int f1 = b.find(b.g[points[i]]);
			int i2 = (i + 1) % points.length;
			int f2 = b.find(b.g[points[i2]]);

			for (int j = 2; j < points.length; j++) {
				int i3 = (i + j) % points.length;
				int k = b.find(b.g[points[i3]]);

				int p = f1;
				while (p != f2) {
					p++;
					p %= b.g.length;
					if (p == k) {
						return true;
					}
				}
			}
		}
		return false;
	}

	abstract public List<Operation> operations();
}