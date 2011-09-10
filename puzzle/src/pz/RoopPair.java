package pz;

public class RoopPair {
	int base;
	int first;
	int second;
	Roop group;
	boolean checked = false;

	public RoopPair(Roop group3, int base) {
		this.base = base;
		int p = -1;
		byte[] r = group3.points;
		for (int i = 0; i < r.length; i++) {
			if (base == r[i]) {
				p = i;
				break;
			}
		}
		if (p == -1) {
			throw new IllegalStateException(group3 + " " + base);
		}
		this.first = r[(p + 1) % 3];
		this.second = r[(p + 2) % 3];
		this.group = group3;
	}

	public boolean isGaoled(Board b) {
		if (!isFirstGaoled(b)) {
			return false;
		}
		if (!isSecondGaoled(b)) {
			return false;
		}
		return true;
	}

	public boolean isFirstGaoled(Board b) {
		return b.g[first] == b.b[first];
	}

	public boolean secondIsFirst(Board b) {
		return (b.g[second] == b.b[first]);
	}

	public boolean firstIsBase(Board b) {
		return (b.g[first] == b.b[base]);
	}

	public boolean firstIsSecond(Board b) {
		return (b.g[first] == b.b[second]);
	}

	public boolean isSecondGaoled(Board b) {
		return b.g[second] == b.b[second];
	}

	public boolean secondIsBase(Board b) {
		return b.g[second] == b.b[base];
	}
}
