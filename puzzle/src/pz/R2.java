package pz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import pz.PanelCommands.CommandInvoker;

public class R2 {

	static public String resolve(Board b) {
		for (CommandInvoker command : b.move0(b.p, b.p(b.H - 1, b.W - 1))) {
			command.invoke(b);
		}
		b.print();
		List<BaseRoop> baseRoops = Roops.collect(b);
		Collection<Roop> makeRoop = Roops.makeRoop(baseRoops, b);
		List<Roop> threeRoops = Roops.changeThreeRoops(baseRoops);
		// for (Roop roop : threeRoops) {
		// System.out.println(roop);
		// }
		int[][] distances = calDistance(b, threeRoops);

		List<Board> list = new ArrayList<Board>();
		list.add(b);
		int roopcount = 0;
		while (true) {
			roopcount++;
			TreeSet<Board> nextList = new TreeSet<Board>();
			for (Board c : list) {
				for (Roop roop : makeRoop) {
					if (!Roops.hasMove0Way(roop, c)) {
						continue;
					}
					Board board = c.clone();
					roop.f(board);
					if (board.goaled()) {
						return board.history();
					}
					board.score = calScore(board, distances);
					if (!nextList.contains(board)) {
						nextList.add(board);
					}
					board = c.clone();
					// invoker.invoke(board);
					roop.f_1(board);
					if (board.goaled()) {
						return board.history();
					}
					board.score = calScore(board, distances);
					if (!nextList.contains(board)) {
						nextList.add(board);
					}
				}
			}
			// Collections.sort(nextList);
			list.clear();
			Board board = nextList.iterator().next();
			System.out.println("min " + board.score);
			board.print();
			int counter = 0;
			for (Board board2 : nextList) {
				list.add(board2);
				counter++;
				if (counter >= 150) {
					break;
				}
			}
			if (roopcount == 20) {
				return "";
			}
		}
	}

	private static int[][] calDistance(Board b, List<Roop> threeRoops) {
		int[][] ranks = new int[b.b.length][b.b.length];
		for (int[] score : ranks) {
			Arrays.fill(score, 0);
		}
		for (Roop roop : threeRoops) {
			for (int i : roop.points) {
				for (int j : roop.points) {
					if (i == j) {
						continue;
					}
					if (ranks[i][j] == 0 || ranks[i][j] > roop.rank()) {
						ranks[i][j] = roop.rank();
					}
				}
			}
		}
		int[][] distances = calDistance(b, ranks);
		return distances;
	}

	private static int[][] calDistance(Board b, int[][] ranks) {
		int[][] distances = new int[b.b.length][b.b.length];
		for (int i = 0; i < distances.length; i++) {
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(i);
			Arrays.fill(distances[i], -1);
			distances[i][i] = 0;
			while (!queue.isEmpty()) {
				int target = queue.removeLast();
				int distance = distances[i][target];
				for (int j = 0; j < distances.length; j++) {
					if (ranks[target][j] != 0 && distances[i][j] == -1) {
						distances[i][j] = 2 * (distance + ranks[target][j]);
						queue.add(j);
					}
				}
			}
		}
		// for (int[] dis : distances) {
		// System.out.println(Arrays.toString(dis));
		// }
		return distances;
	}

	private static int calScore(Board b, int[][] distances) {
		int score = 0;
		for (int i = 0; i < b.b.length; i++) {
			int from = b.find(b.g[i]);
			int to = i;
			if (from != -1) {
				score += distances[from][to];
			}
		}
		return score;
	}
}
