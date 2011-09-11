package pz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pz.PanelCommands.CommandInvoker;

public class R2 {
	static ExecutorService service = Executors.newFixedThreadPool(16);

	static public String resolve(Board b) throws InterruptedException,
			ExecutionException {
		for (CommandInvoker command : b.move0(b.p, b.p(b.H - 1, b.W - 1))) {
			command.invoke(b);
		}
		b.print();
		List<BaseRoop> baseRoops = Roops.collect(b);
		Collection<Roop> makeRoop = Roops.makeRoop(baseRoops, b);
		List<Roop> threeRoops = Roops.changeThreeRoops(baseRoops);
		int[][] distances = calDistance(b, threeRoops);

		TreeSet<Board> list = new TreeSet<Board>();
		list.add(b);
		b.score = Integer.MAX_VALUE;
		int roopcount = 0;
		HashSet<String> keys = new HashSet<String>();
		try {
			while (true) {
				roopcount++;
				TreeSet<Board> nextList = new TreeSet<Board>();
				// for (int i = 0; i < 3; i++)
				{
					for (Board c : list) {
						ArrayList<Future<Board>> features = new ArrayList<Future<Board>>();
						for (final Roop roop : makeRoop) {
							if (!Roops.hasMove0Way(roop, c)) {
								continue;
							}
							final Board board = c.clone();

							Future<Board> future = service
									.submit(new Callable<Board>() {

										@Override
										public Board call() throws Exception {
											roop.f(board);
											return board;
										}
									});
							features.add(future);

							final Board board2 = c.clone();
							Future<Board> future2 = service
									.submit(new Callable<Board>() {
										@Override
										public Board call() throws Exception {
											roop.f_1(board2);
											return board2;
										}
									});
							features.add(future2);

						}
						for (Future<Board> feature : features) {
							Board board = feature.get();
							if (board.goaled()) {
								return board.history();
							}
							String key = board.key();
							if (keys.contains(key)) {
								continue;
							}
							keys.add(key);
							board.score = calScore(board, distances);
							if (!nextList.contains(board)) {
								nextList.add(board);
							}
						}
					}
					// list.addAll(nextList);
				}
				list.clear();
				if (roopcount % 3 == 0) {
					Board board = nextList.iterator().next();
					System.out.println("min " + board.score);
					board.print();
				}
				int counter = 0;
				for (Board board2 : nextList) {
					list.add(board2);
					counter++;
					if (counter >= 500) {
						break;
					}
				}

				if (roopcount == 50) {
					return "";
				}
			}
		} finally {
			// service.shutdown();
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
			if (b.fixed[i]) {
				continue;
			}
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(i);
			Arrays.fill(distances[i], -1);
			distances[i][i] = 0;
			int h = Math.abs(b.H - b.h(i) + 1);
			int w = Math.abs(b.W - b.w(i) + 1);
			int count = 5 - b.commands[i].enableCommandCount(b);

			while (!queue.isEmpty()) {
				int target = queue.removeLast();
				int distance = distances[i][target];
				for (int j = 0; j < distances.length; j++) {
					if (ranks[target][j] != 0 && distances[i][j] == -1) {
						distances[i][j] = distance + (ranks[target][j])
								* (h + w) * (h + w) * count;
						queue.add(j);
					}
				}
			}
		}
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
