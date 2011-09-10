package pz;

import java.util.Arrays;
import java.util.List;

import pz.PanelCommands.CommandInvoker;

public class BaseRoop extends Roop {

	byte entry;
	Operation[] operations;

	public BaseRoop(byte entry, byte[] points, Operation[] operations) {
		super();
		this.points = points;
		this.entry = entry;
		this.operations = operations;
		initPoint(points);
	}

	public String toString() {
		return rank() + ":" + entry + ":" + Arrays.toString(points);
	}

	public void f(Board board) {
		byte[] cp = null;
		if (DEBUG) {
			cp = new byte[board.b.length];
			System.arraycopy(board.b, 0, cp, 0, cp.length);
			f(cp);
		}
		List<CommandInvoker> invokers = moveEntryPoint(board);
		for (Operation op : operations) {
			board.command(op);
		}
		returnBasePoint(board, invokers);
		if (DEBUG) {
			String ex = toString(cp);
			String ac = toString(board.b);
			if (!ex.equals(ac)) {
				System.out.println(ex);
				System.err.println(ac);
				throw new IllegalStateException();
			}
		}
	}

	public void f_1(Board board) {
		byte[] cp = null;
		if (DEBUG) {
			cp = new byte[board.b.length];
			System.arraycopy(board.b, 0, cp, 0, cp.length);
			f_1(cp);
		}
		List<CommandInvoker> invokers = moveEntryPoint(board);
		for (int i = operations.length - 1; i >= 0; i--) {
			board.command(operations[i].f_1());
		}
		returnBasePoint(board, invokers);
		if (DEBUG) {
			String ex = toString(cp);
			String ac = toString(board.b);
			if (!ex.equals(ac)) {
				System.out.println(ex);
				System.err.println(ac);
				throw new IllegalStateException();
			}
		}
	}

	private void returnBasePoint(Board board, List<CommandInvoker> invokers) {
		for (int i = invokers.size() - 1; i >= 0; i--) {
			invokers.get(i).reversCommand().invoke(board);
		}
	}

	private List<CommandInvoker> moveEntryPoint(Board board) {
		for (int k : points) {
			board.fixed[k] = true;
		}
		List<CommandInvoker> invokers = board.move0(board.p, entry);
		for (int k : points) {
			board.fixed[k] = false;
		}
		for (CommandInvoker invoker : invokers) {
			invoker.invoke(board);
		}
		return invokers;
	}

	public int threePiarsSize() {
		if (points.length == 3) {
			return 1;
		}
		return points.length;
	}

	public byte[][] threePairs() {
		if (points.length == 3) {
			return new byte[][] { points };
		}
		byte[][] pairs = new byte[points.length][3];
		for (int i = 0; i < points.length - 2; i++) {
			pairs[i][0] = points[i];
			pairs[i][1] = points[i + 1];
			pairs[i][2] = points[i + 2];

		}
		pairs[points.length - 2][0] = points[points.length - 2];
		pairs[points.length - 2][1] = points[points.length - 1];
		pairs[points.length - 2][2] = points[0];

		pairs[points.length - 1][0] = points[points.length - 1];
		pairs[points.length - 1][1] = points[0];
		pairs[points.length - 1][2] = points[1];

		return pairs;
	}

	@Override
	public int rank() {
		return operations.length;
	}

	@Override
	public List<BaseRoop> base() {
		return Arrays.asList(this);
	}

}
