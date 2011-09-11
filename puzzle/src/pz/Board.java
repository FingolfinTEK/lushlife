package pz;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import pz.PanelCommands.CommandInvoker;

public class Board implements Comparable<Board> {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(b);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.equals(b, other.b))
			return false;
		return true;
	}

	private static final boolean DEBUG = false;
	int score;
	String s;
	byte W, H;
	int L, U, R, D;
	byte[] b;
	byte[] a;
	byte[] g;
	boolean[] fixed;
	int p;
	Puzzle game;
	PanelCommands[] commands;
	ArrayList<Operation> operationHistory = new ArrayList<Operation>();
	public int counter;
	private Puzzle puzzle;
	int panelCount;
	byte checkdPanel = -1;

	public Board(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public byte home() {
		return (byte) (H * W - 1);
	}

	public Board clone() {
		Board board = new Board(puzzle);
		board.s = s;
		board.p = p;
		board.W = W;
		board.H = H;

		board.L = L;
		board.U = U;
		board.R = R;
		board.D = D;

		board.b = new byte[b.length];
		board.g = this.g;
		board.counter = this.counter;
		System.arraycopy(b, 0, board.b, 0, board.b.length);
		board.operationHistory.addAll(this.operationHistory);

		board.fixed = new boolean[b.length];
		System.arraycopy(fixed, 0, board.fixed, 0, board.fixed.length);

		board.commands = this.commands;
		board.game = game;
		return board;
	}

	public void command(String command) {
		for (char c : command.toCharArray()) {
			Operation op = Operation.valueOf(c + "");
			command(op);
		}
	}

	public void init(String s) {
		this.s = s;
		String[] brs = s.split(",");
		this.W = (byte) Integer.parseInt(brs[0]);
		this.H = (byte) Integer.parseInt(brs[1]);
		char[] array = brs[2].toCharArray();
		this.b = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			this.b[i] = (byte) array[i];
		}
		this.fixed = new boolean[b.length];
		this.g = new byte[b.length];
		this.panelCount = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] == '0') {
				this.p = i;
			}
			if (b[i] == '=') {
				this.fixed[i] = true;
				this.g[i] = '=';
			} else {
				this.g[i] = g(i);
				panelCount++;
			}
		}
		this.a = new byte[g.length];
		for (int i = 0; i < g.length; i++) {
			a[i] = g[find(g[i])];
		}
		this.commands = new PanelCommands[this.b.length];
		for (int i = 0; i < this.b.length; i++) {
			this.commands[i] = new PanelCommands(i, this);
		}
	}

	byte g(int i) {
		if (i == W * H - 1) {
			return '0';
		}
		if (0 <= i && i <= 8) {
			return (byte) ('1' + i);
		}
		return (byte) ('A' + i - 9);
	}

	public void print() {
		PrintStream ps = System.out;
		for (int i = 0; i < H; i++) {
			ps.printf("%2d", i * W);
			ps.print(" ");

			for (int j = 0; j < W; j++) {
				ps.print((char) g[W * i + j]);
			}
			/*
			 * if (a != null) { ps.print(" ");
			 * 
			 * for (int j = 0; j < W; j++) { ps.print((char) a[W * i + j]); } }
			 */
			ps.print(" ");
			for (int j = 0; j < W; j++) {
				ps.print((char) b[W * i + j]);
			}

			ps.print(" ");
			for (int j = 0; j < W; j++) {
				if (g[W * i + j] == b[W * i + j] && b[W * i + j] != '=') {
					ps.print("G");
				} else {
					ps.print("-");
				}
			}
			ps.print(" ");
			for (int j = 0; j < W; j++) {
				ps.print(fixed[W * i + j] ? "F" : "-");
			}

			ps.print(" ");
			for (int j = 0; j < W; j++) {
				ps.print(commands[W * i + j].enableCommandCount(this));
			}

			ps.print(" ");
			ps.printf(" %2d", i * W + W - 1);
			ps.println();

		}
		System.out.println("--------");
		System.out.println(counter);
		System.out.println("L " + L + " R " + R + " U " + U + " D " + D);
		System.out.println(checkdPanel);
		// String move = move();
		// System.out.println("Repeat  " + move);
		// String r = history.substring(move.length(),
		// history.length() - move.length());
		// System.out.println("Rotation " + r);
		System.out.println("--------");
	}

	public int[] sequence() {
		int r = 0;
		int[] seq = new int[fixed.length];
		int count = 0;
		while (count < seq.length) {
			int p = p(r, r);
			seq[count++] = p;
			int h = r + 1;
			int w = r + 1;
			while (h < H || w < W) {
				int p1 = p(h, r);
				if (p1 != -1) {
					seq[count++] = p1;
					h++;
				}
				int p2 = p(r, w);
				if (p2 != -1) {
					seq[count++] = p2;
					w++;
				}
			}
			r++;

		}
		return seq;
	}

	boolean enableU() {
		int th = p / W;
		if (th == 0) {
			return false;
		}
		if (b[p - W] == '=') {
			return false;
		}
		return true;
	}

	boolean enableD() {
		int th = p / W;
		if (th == H - 1) {
			return false;
		}
		if (b[p + W] == '=') {
			return false;
		}
		return true;
	}

	boolean enableL() {
		int tw = p % W;
		if (tw == 0) {
			return false;
		}
		if (b[p - 1] == '=') {
			return false;
		}
		return true;
	}

	boolean enableR() {
		int tw = p % W;
		if (tw == W - 1) {
			return false;
		}
		if (b[p + 1] == '=') {
			return false;
		}
		return true;
	}

	void swap(int i, int j) {
		byte t = b[i];
		b[i] = b[j];
		b[j] = t;
	}

	int L() {
		if (!enableL()) {
			throw new IllegalStateException();
		}
		Operation lastOperation = getLast();
		if (lastOperation != null && lastOperation.f_1().equals(Operation.L)) {
			removeLast();
			counter--;
			R--;
		} else {
			operationHistory.add(commands[p].L.command);
			counter++;
			L++;
		}
		swap(p, p - 1);
		int tmp = p;
		p = p - 1;
		return tmp;
	}

	private void removeLast() {
		operationHistory.remove(operationHistory.size() - 1);
	}

	Operation getLast() {
		if (operationHistory.size() == 0) {
			return null;
		}
		return operationHistory.get(operationHistory.size() - 1);
	}

	int U() {
		if (!enableU()) {
			throw new IllegalStateException();
		}
		Operation lastOperation = getLast();
		if (lastOperation != null && lastOperation.f_1().equals(Operation.U)) {
			removeLast();
			counter--;
			D--;

		} else {
			operationHistory.add(commands[p].U.command);
			counter++;
			U++;
		}
		swap(p, p - W);
		int tmp = p;
		p = p - W;
		return tmp;
	}

	int R() {
		if (!enableR()) {
			throw new IllegalStateException();
		}
		Operation lastOperation = getLast();
		if (lastOperation != null && lastOperation.f_1().equals(Operation.R)) {
			removeLast();
			counter--;
			L--;
		} else {
			operationHistory.add(commands[p].R.command);
			counter++;
			R++;
		}
		swap(p, p + 1);
		int tmp = p;
		p = p + 1;
		return tmp;

	}

	int D() {
		if (!enableD()) {
			throw new IllegalStateException();
		}
		Operation lastOperation = getLast();
		if (lastOperation != null && lastOperation.f_1().equals(Operation.D)) {
			removeLast();
			counter--;
			U--;
		} else {
			operationHistory.add(commands[p].D.command);
			counter++;
			D++;
		}
		swap(p, p + W);
		int tmp = p;
		p = p + W;
		return tmp;
	}

	int command(Operation c) {
		switch (c) {
		case L:
			return L();
		case R:
			return R();
		case U:
			return U();
		case D:
			return D();
		}
		throw new IllegalStateException();
	}

	int h(int p) {
		return p / W;
	}

	int w(int p) {
		return p % W;
	}

	int p(int h, int w) {
		if (h < 0 || h >= H) {
			return -1;
		}
		if (w < 0 || w >= W) {
			return -1;
		}
		int p = h * this.W + w;
		return p;
	}

	int r(int p) {
		int w = w(p);
		if (w == W - 1) {
			return -1;
		}
		int h = h(p);
		return p(h, w + 1);
	}

	int l(int p) {
		int w = w(p);
		if (w == 0) {
			return -1;
		}
		int h = h(p);
		return p(h, w - 1);
	}

	int u(int p) {
		int h = h(p);
		if (h == 0) {
			return -1;
		}
		int w = w(p);
		return p(h - 1, w);
	}

	int d(int p) {
		int h = h(p);
		if (h == H - 1) {
			return -1;
		}
		int w = w(p);
		return p(h + 1, w);
	}

	List<CommandInvoker> commands(int i) {
		return commands[i].enableCommands(this);
	}

	public List<CommandInvoker> commands() {
		return commands(p);
	}

	List<CommandInvoker> commandsWithoutLast(int i) {
		return commands[i].enableCommandsWithoutLastOperation(this);
	}

	public List<CommandInvoker> commandsWithoutLast() {
		return commandsWithoutLast(p);
	}

	public String key() {
		StringBuilder sb = new StringBuilder();
		for (byte s : b) {
			sb.append((char) s);
		}
		return sb.toString();
	}

	List<CommandInvoker> move0(int from, int to) {
		List<CommandInvoker> invokers = new ArrayList<CommandInvoker>();
		if (from == to) {
			return invokers;
		}

		boolean[] fixedBackup = this.fixed;
		this.fixed = new boolean[fixedBackup.length];
		System.arraycopy(fixedBackup, 0, this.fixed, 0, fixedBackup.length);

		int[] distance = createDistanceMatrics(to);
		if (distance[from] == -1) {
			if (DEBUG) {
				print();
				System.out.println("don't move: from " + from + " to " + to
						+ " " + Arrays.toString(distance));
			}
			return null;
		}
		int c = from;
		while (c != to) {
			CommandInvoker invoker = null;
			int d = Integer.MAX_VALUE;
			for (CommandInvoker i : commands[c].enableCommands(this)) {
				int p = i.command.move(c, this);
				if (distance[p] < d) {
					d = distance[p];
					invoker = i;
				}
			}
			if (invoker == null) {
				print();
				throw new IllegalStateException(c + " " + invokers);
			}
			invokers.add(invoker);
			c = invoker.command.move(c, this);
		}
		this.fixed = fixedBackup;
		return invokers;
	}

	private int[] createDistanceMatrics(int to) {
		int[] distance = new int[this.b.length];
		Arrays.fill(distance, -1);
		distance[to] = 0;
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(to);
		while (!queue.isEmpty()) {
			int target = queue.removeFirst();
			int dis = distance[target];
			if (dis == -1) {
				throw new IllegalStateException();
			}
			for (CommandInvoker invoker : commands[target].enableCommands(this)) {
				int p = invoker.command.move(target, this);
				if (distance[p] == -1) {
					distance[p] = dis + 1;
					queue.add(p);
				}
			}
		}
		return distance;
	}

	public int find(byte c) {
		for (int i = 0; i < b.length; i++) {
			if (b[i] == c) {
				return i;
			}
		}
		return -1;
	}

	public String history() {
		StringBuilder sb = new StringBuilder();
		for (Operation op : operationHistory) {
			sb.append(op.name());
		}
		return sb.toString();
	}

	public void move(byte target, int to) {
		int current = p;
		int from = find(target);
		if (from == to) {
			return;
		}

		boolean[] fixedBackup = this.fixed;
		this.fixed = new boolean[fixedBackup.length];
		System.arraycopy(fixedBackup, 0, this.fixed, 0, fixedBackup.length);

		int[] distance = createDistanceMatrics(to);
		if (distance[from] == -1) {
			if (DEBUG) {
				print();
				System.out.println("don't move: from " + from + " to " + to
						+ " " + Arrays.toString(distance));
			}
		}
		ArrayList<CommandInvoker> invokers = new ArrayList<CommandInvoker>();
		int c = from;
		while (c != to) {
			CommandInvoker invoker = null;
			int d = Integer.MAX_VALUE;
			for (CommandInvoker i : commands[c].enableCommands(this)) {
				int p = i.command.move(c, this);
				if (distance[p] == -1) {
					throw new IllegalStateException();
				}
				if (distance[p] < d) {
					d = distance[p];
					invoker = i;
				}
			}
			if (invoker == null) {
				print();
				throw new IllegalStateException(c + " " + invokers);
			}
			invokers.add(invoker.reversCommand());
			c = invoker.command.move(c, this);
		}
		this.fixed = fixedBackup;
		for (CommandInvoker invoker : invokers) {
			fixed[invoker.target()] = true;
			List<CommandInvoker> move0 = move0(p, invoker.panelNumber());
			try {
				if (move0 == null) {
					throw new IllegalStateException();
				}
				for (CommandInvoker i : move0) {
					i.invoke(this);
				}
				fixed[invoker.target()] = false;
				invoker.invoke(this);
			} finally {
				fixed[invoker.target()] = false;
			}
		}
		if (DEBUG) {
			System.out.println("target " + target + " to " + to);
		}
		fixed[to] = true;
		try {
			List<CommandInvoker> move0 = move0(p, current);
			if (move0 == null) {
				throw new IllegalStateException();
			}
			for (CommandInvoker i : move0) {
				i.invoke(this);
			}
		} finally {
			fixed[to] = false;
		}
	}

	public String g() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < g.length; i++) {
			sb.append((char) g[i]);
		}
		return sb.toString();
	}

	public String b() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append((char) b[i]);
		}
		return sb.toString();
	}

	public String a() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			if (a[i] == g[i]) {
				sb.append("-");
			} else {
				sb.append((char) a[i]);
			}
		}
		return sb.toString();
	}

	public void historyClear() {
		operationHistory.clear();
		counter = 0;
		L = 0;
		R = 0;
		U = 0;
		D = 0;

	}

	public boolean goaled() {
		for (int i = 0; i < g.length; i++) {
			if (g[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(Board o) {
		return score() - o.score();
	}

	private int score() {
		return score;
	}
}
