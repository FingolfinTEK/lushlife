package pz;

import java.util.ArrayList;
import java.util.List;

class PanelCommands {
	Board board;

	class CommandInvoker {
		Operation command;

		public CommandInvoker(Operation command) {
			this.command = command;
		}

		public int invoke(Board board) {
			if (!enable(board)) {
				throw new IllegalStateException();
			}
			return board.command(command);
		}

		public int panelNumber() {
			return panelNumber;
		}

		public int target() {
			return target(panelNumber);
		}

		public int target(int num) {
			int v = -1;
			switch (command) {
			case D:
				v = board.d(num);
				break;
			case L:
				v = board.l(num);
				break;
			case R:
				v = board.r(num);
				break;
			case U:
				v = board.u(num);
				break;
			}
			return v;
		}

		boolean enable(Board board) {
			return enable(panelNumber, board);
		}

		boolean enable(int num, Board board) {
			int v = target(num);
			if (v == -1) {
				return false;
			}
			return board.fixed[v] == false && board.fixed[panelNumber] == false;
		}

		public String toString() {
			int h = board.h(panelNumber);
			int w = board.w(panelNumber);
			return command.name() + "(" + h + "," + w + ")";
		}

		public CommandInvoker reversCommand() {
			switch (command) {
			case L:
				return board.commands[target()].R;
			case U:
				return board.commands[target()].D;
			case R:
				return board.commands[target()].L;
			case D:
				return board.commands[target()].U;
			}
			return null;
		}
	}

	int panelNumber;
	CommandInvoker L = new CommandInvoker(Operation.L);
	CommandInvoker R = new CommandInvoker(Operation.R);
	CommandInvoker U = new CommandInvoker(Operation.U);
	CommandInvoker D = new CommandInvoker(Operation.D);

	public PanelCommands(int panel, Board board) {
		this.panelNumber = panel;
		this.board = board;
	}

	public List<CommandInvoker> enableCommandsWithoutLastOperation(Board board) {
		List<CommandInvoker> invokers = new ArrayList<PanelCommands.CommandInvoker>();
		Operation last = board.getLast();
		if (L.enable(board)) {
			if (last == null || !last.f_1().equals(L)) {
				invokers.add(L);
			}
		}
		if (R.enable(board)) {
			if (last == null || !last.f_1().equals(R)) {
				invokers.add(R);
			}
		}
		if (U.enable(board)) {
			if (last == null || !last.f_1().equals(U)) {
				invokers.add(U);
			}
		}
		if (D.enable(board)) {
			if (last == null || !last.f_1().equals(D)) {
				invokers.add(D);
			}
		}

		return invokers;
	}

	public List<CommandInvoker> enableCommands(Board board) {
		List<CommandInvoker> invokers = new ArrayList<PanelCommands.CommandInvoker>();
		if (L.enable(board)) {
			invokers.add(L);
		}
		if (R.enable(board)) {
			invokers.add(R);
		}
		if (U.enable(board)) {
			invokers.add(U);
		}
		if (D.enable(board)) {
			invokers.add(D);
		}

		return invokers;
	}

	public int enableCommandCount(Board board) {
		if (board.fixed[panelNumber]) {
			return 0;
		}
		int enableCommandNum = 0;
		if (L.enable(board)) {
			enableCommandNum++;
		}
		if (R.enable(board)) {
			enableCommandNum++;
		}
		if (U.enable(board)) {
			enableCommandNum++;
		}
		if (D.enable(board)) {
			enableCommandNum++;
		}
		return enableCommandNum;
	}
}