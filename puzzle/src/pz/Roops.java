package pz;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import pz.PanelCommands.CommandInvoker;

public class Roops {

	static public List<BaseRoop> collect(Board board) {
		ArrayList<BaseRoop> roops = new ArrayList<BaseRoop>();
		for (int i = 0; i < board.H; i++) {
			for (int j = 0; j < board.W; j++) {
				int entry = board.p(i, j);
				if (board.b[entry] == '=') {
					continue;
				}
				for (Operation op : Operation.values()) {
					Operation[] operations = findRoop(board, entry, op);
					if (operations == null) {
						continue;
					}
					byte[] way = way(board, entry, operations);
					BaseRoop roop = new BaseRoop((byte) entry, way, operations);

					if (roop.constains(board.home())) {
						continue;
					}
					if (roops.contains(roop)) {
						continue;

					}
					if (!hasMove0Way(roop, board)) {
						continue;
					}
					if (!roops.contains(roop)) {
						roops.add(roop);
					}
				}
			}
		}
		return roops;
	}

	public static byte[] way(Board board, int entry, Operation[] operations) {
		byte[] way = new byte[operations.length - 1];
		for (int i = 0; i < way.length; i++) {
			entry = operations[i].move(entry, board);
			way[i] = (byte) entry;
		}
		return way;
	}

	public static List<Roop> removeNoWay(List<Roop> roops, Board board) {
		List<Roop> list = new ArrayList<Roop>();
		MAIN: for (Roop roop : roops) {
			for (BaseRoop base : roop.base()) {
				if (!hasMove0Way(base, board)) {
					continue MAIN;
				}
			}
			list.add(roop);
		}
		return list;
	}

	public static boolean hasMove0Way(Roop roop, Board board) {
		for (int r : roop.points) {
			board.fixed[r] = true;
		}
		List<CommandInvoker> move0 = board.move0(board.home(), roop.entry);
		for (int r : roop.points) {
			board.fixed[r] = false;
		}
		return move0 != null;
	}

	static public List<Roop> changeThreeRoops(List<BaseRoop> roops) {
		ArrayList<Roop> list = new ArrayList<Roop>();
		for (BaseRoop roop : roops) {
			if (roop.threePiarsSize() == 1) {
				list.add(roop);
				continue;
			}
			for (BaseRoop r2 : roops) {
				if (roop == r2) {
					continue;
				}
				if (roop.and(r2).size() != 1) {
					continue;
				}
				if (r2.threePiarsSize() != 1) {
					continue;
				}
				for (byte[] treePairs : roop.threePairs()) {
					CompositRoop subRoop = r2.createSubRoop(roop, treePairs);
					if (!list.contains(subRoop)) {
						list.add(subRoop);
					}
				}
			}
		}
		return list;
	}

	static public Roop[] changeBase(List<? extends Roop> roops, byte base) {
		TreeSet<Roop> groupSet = new TreeSet<Roop>();
		HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
		groupSet.addAll(roops);
		TreeSet<Byte> targetList = new TreeSet<Byte>();
		for (Roop roop : roops) {
			if (roop.constains(base)) {
				continue;
			}
			for (byte b : roop.points) {
				targetList.add(b);
			}
		}
		targetList.remove(base);
		while (true) {
			ArrayList<Roop> addList = new ArrayList<Roop>();
			for (Roop group : groupSet) {
				if (!group.constains(base)) {
					continue;
				}

				for (Roop group2 : groupSet) {
					if (group2 == group) {
						continue;
					}
					if (group.and(group2).size() != 1) {
						continue;
					}
					CompositRoop roop = group.baseChange(group2, base);
					if (!groupSet.contains(roop)) {
						String key = roop.pointKey();
						if (scoreMap.containsKey(key)) {
							int score = scoreMap.get(key);
							if (roop.rank() > score) {
								continue;
							}
						}
						scoreMap.put(key, roop.rank());
						for (byte b : roop.points) {
							targetList.remove(b);
						}
						addList.add(roop);

					}
				}
			}
			if (addList.size() == 0) {
				break;
			} else {
				groupSet.addAll(addList);
			}
			if (targetList.size() == 0) {
				break;
			}
		}
		ArrayList<Roop> output = new ArrayList<Roop>();
		for (Roop roop : groupSet) {
			if (roop.constains(base)) {
				output.add(roop);
			}
		}
		return output.toArray(new Roop[0]);
	}

	static public Roop[] makeMinimumBase(Board board, List<Roop> roops,
			byte base) {
		Roop[] baseChangeGroups = changeBase(roops, base);
		if (baseChangeGroups.length == 0) {
			return null;
		}
		ArrayList<Roop> output = toMinimumBase(base, roops, baseChangeGroups,
				board);
		return output.toArray(new Roop[0]);
	}

	static private Comparator<Roop> createComparator(Board board) {
		final int[] seq = board.sequence();
		return new Comparator<Roop>() {
			@Override
			public int compare(Roop x, Roop y) {
				if (x.rank() != y.rank()) {
					return x.rank() - y.rank();
				}
				return seq[x.points[0]] - seq[y.points[0]];
			}
		};
	}

	static private ArrayList<Roop> toMinimumBase(byte base,
			List<? extends Roop> threePairs, Roop[] baseChangeGroups,
			Board board) {
		Arrays.sort(baseChangeGroups, createComparator(board));
		boolean[] panelCheck = new boolean[board.b.length];
		System.arraycopy(board.fixed, 0, panelCheck, 0, board.b.length);

		ArrayList<Roop> output = new ArrayList<Roop>();
		panelCheck[board.home()] = true;
		if (board.checkdPanel != -1) {
			panelCheck[board.checkdPanel] = true;
		}

		MAIN: for (Roop g : baseChangeGroups) {
			for (int r : g.points) {
				if (r == base) {
					continue;
				}
				if (panelCheck[r]) {
					continue MAIN;
				}
			}
			for (int r : g.points) {
				panelCheck[r] = true;
			}
			output.add(g);
		}
		LinkedList<Byte> l = new LinkedList<Byte>();
		for (byte i = 0; i < panelCheck.length; i++) {
			if (!panelCheck[i]) {
				l.add(i);
			}
		}
		// System.out.println(base + " Žc‚è " + l);
		int size = l.size();
		if (size >= 2) {
			MAIN: for (int i = 0; i < size - 1; i += 2) {
				byte first = l.removeFirst();
				byte second = l.removeFirst();
				Roop[] firstBase = changeBase(threePairs, first);
				Arrays.sort(firstBase, createComparator(board));
				Roop target = null;
				for (Roop g : firstBase) {
					if (g.constains(second) && g.constains(base)) {
						output.add(g);
						continue MAIN;
					}
					if (target == null && g.constains(second)) {
						target = g;
					}
				}
				if (target == null) {
					// throw new IllegalStateException(base + ":" + first + ":"
					// + second);
					continue MAIN;
				}
				byte other = target.other(first, second);
				for (Roop g : baseChangeGroups) {
					if (g.constains(other) && !g.constains(first)
							&& !g.constains(second)) {
						if (g.and(target).size() == 1) {
							Roop t = g.baseChange(target, base);
							output.add(t);
							continue MAIN;
						}
					}
				}
			}
		}
		return output;
	}

	static public List<Integer> findBridgePoint(Board board) {
		List<Integer> list = new ArrayList<Integer>();
		Board b = board.clone();
		int blockSize = block(board).size();
		TreeSet<Integer> notTarget = new TreeSet<Integer>();
		MAIN: for (int i = 1; i < b.b.length - 2; i++) {
			if (b.fixed[i]) {
				continue;
			}
			if (b.commands[i].enableCommandCount(b) != 2) {
				continue;
			}
			if (notTarget.contains(i)) {
				continue;
			}
			b.fixed[i] = true;
			try {

				int newBlockSize = block(b).size();
				if (blockSize != newBlockSize) {
					list.add(i);
					notTarget.add(i);
					boolean addFrag = true;
					while (addFrag) {
						addFrag = false;
						for (int k : notTarget) {
							b.fixed[k] = true;
						}
						for (int k = 0; k < b.b.length; k++) {
							if (b.commands[k].enableCommandCount(b) == 1) {
								notTarget.add(k);
								addFrag = true;
							}
						}
						for (int k : notTarget) {
							b.fixed[k] = false;
						}
					}
					continue MAIN;
				}
			} finally {
				b.fixed[i] = false;
			}
		}
		return list;
	}

	public static ArrayList<TreeSet<Integer>> block(Board board) {
		Board b = board.clone();
		ArrayList<TreeSet<Integer>> blocks = new ArrayList<TreeSet<Integer>>();
		MAIN: while (true) {
			for (int i = 0; i < b.b.length; i++) {
				if (b.fixed[i] == false) {
					TreeSet<Integer> block = new TreeSet<Integer>();
					LinkedList<Integer> stack = new LinkedList<Integer>();
					stack.add(i);
					while (!stack.isEmpty()) {
						int p = stack.removeLast();
						block.add(p);
						for (CommandInvoker c : b.commands[p].enableCommands(b)) {
							stack.add(c.target(p));
						}
						b.fixed[p] = true;
					}
					blocks.add(block);
					continue MAIN;
				}
			}
			break;
		}
		return blocks;
	}

	public static <T extends Roop> List<T> remove(List<T> threeRoops, byte value) {
		List<T> list = new ArrayList<T>();
		for (T roop : threeRoops) {
			if (!roop.constains(value)) {
				list.add(roop);
			}
		}
		return list;
	}

	static public Operation[] findRoop(Board board, int entry,
			Operation startOperation) {
		TreeSet<Integer> checkd = new TreeSet<Integer>();
		List<Operation> operations = new ArrayList<Operation>();
		Operation operation = startOperation;
		operations.add(operation);
		int p = operation.move(entry, board);
		if (p == -1 || board.b[p] == '=') {
			return null;
		}
		MAIN: while (p != entry) {
			for (Operation o : operation.forwardRotation()) {
				int p0 = o.move(p, board);
				if (p0 != -1 && board.fixed[p0] == false
						&& !checkd.contains(p0)) {
					operation = o;
					operations.add(operation);
					p = p0;
					checkd.add(p);
					continue MAIN;
				}
			}
			return null;
		}

		return operations.toArray(new Operation[0]);
	}

	static public BaseRoop add(Roop a, Roop b, Board board) {
		List<Operation> operations = a.operations();
		List<Operation> op = new ArrayList<Operation>();
		int p = a.entry;
		for (int i = 0; i < operations.size() - 2; i++) {
			p = operations.get(i).move(p, board);
			op.add(operations.get(i));
			if (p == b.entry) {
				int next = operations.get(i + 1).move(p, board);
				if (b.last() == next) {
					op.addAll(b.operations());
					op.remove(op.size() - 1);
					for (int j = i + 2; j < operations.size(); j++) {
						op.add(operations.get(j));
					}
					Operation[] array = op.toArray(new Operation[0]);
					byte[] way = way(board, a.entry, array);
					return new BaseRoop(a.entry, way, array);
				}
				break;
			}
		}
		// throw new IllegalArgumentException();
		return null;
	}

	static public CompositRoop join(Roop a, Roop b, Board board) {
		if (!roopCheck(a, b)) {
			return null;
		}
		Roop x = null, y = null;
		if (a.last() == b.first()) {
			x = a;
			y = b;
		}
		if (a.first() == b.last()) {
			y = a;
			x = b;
		}
		if (x == null) {
			return null;
		}
		Function[] f = new Function[2];
		f[0] = x.f();
		f[1] = y.f();
		return new CompositRoop(x.join(y), f, x.entry);

	}

	private static boolean roopCheck(Roop a, Roop b) {
		if (a.entry != b.entry) {
			return false;
		}
		if (a.and(b).size() != 1) {
			return false;
		}
		return true;
	}

	private static void swap(byte[] points, int i, int j) {
		byte tmp = points[i];
		points[i] = points[j];
		points[j] = tmp;
	}

	public static String toString(List<Operation> operations) {
		StringBuilder sb = new StringBuilder();
		for (Operation operation : operations) {
			sb.append(operation.name());
		}
		return sb.toString();
	}

	public static TreeSet<Roop> makeRoop(List<BaseRoop> baseRoops,
			final Board board) throws InterruptedException, ExecutionException {
		TreeSet<Roop> roops = new TreeSet<Roop>();
		TreeSet<Roop> newRoop = new TreeSet<Roop>();
		roops.addAll(baseRoops);
		newRoop.addAll(baseRoops);
		while (!newRoop.isEmpty()) {

			List<Roop> target = new ArrayList<Roop>();
			target.addAll(newRoop);
			roops.addAll(newRoop);
			newRoop.clear();
			if (roops.size() >= 888) {
				break;
			}
			List<Future<Roop>> futures = new ArrayList<Future<Roop>>();
			MAIN: for (final Roop roop2 : target) {
				for (final Roop roop : roops) {

					if (futures.size() > 888) {
						break MAIN;
					}
					if (roop.equals(roop2)) {
						continue;
					}

					if (roop.and(roop2).size() == 0) {
						continue;
					}

					Future<Roop> f1 = R2.service.submit(new Callable<Roop>() {

						@Override
						public Roop call() throws Exception {
							return add(roop, roop2, board);
						}
					});
					Future<Roop> f2 = R2.service.submit(new Callable<Roop>() {

						@Override
						public Roop call() throws Exception {
							return add(roop, roop2, board);
						}
					});
					Future<Roop> f3 = R2.service.submit(new Callable<Roop>() {

						@Override
						public Roop call() throws Exception {
							return join(roop2, roop, board);
						}
					});
					futures.add(f1);
					futures.add(f2);
					futures.add(f3);

				}
				int counter = 0;
				for (Future<Roop> feature : futures) {
					if (++counter % 1000000 == 0) {
						System.out.println(counter);
					}
					if (newRoop.size() > 999) {
						feature.cancel(false);
						continue;
					}
					Roop r = feature.get();
					if (r != null && !roops.contains(r)) {
						newRoop.add(r);
					}

				}
			}
		}

		return roops;
	}
}
