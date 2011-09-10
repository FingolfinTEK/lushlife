package pz;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pz.PanelCommands.CommandInvoker;

public class Resolver {
	static boolean DEBUG = false;

	static public String resolve(Board b) {
		for (CommandInvoker command : b.move0(b.p, b.p(b.H - 1, b.W - 1))) {
			command.invoke(b);
		}
		if (DEBUG) {
			b.print();
		}
		List<BaseRoop> baseRoops = Roops.collect(b);
		List<Roop> threeRoops = Roops.changeThreeRoops(baseRoops);
		// threeRoops = removePoints(b, baseRoops, threeRoops);
		if (DEBUG) {
			System.out.println("***************");
			System.out.println("Base Roop");
			System.out.println("***************");
			for (BaseRoop base : baseRoops) {
				System.out.println(base);
			}
			System.out.println("***************");
		}
		List<Integer> findBridgePoint = Roops.findBridgePoint(b);
		if (findBridgePoint.size() >= 2) {
			throw new IllegalStateException(findBridgePoint.toString());
		}
		if (findBridgePoint.size() == 0) {
			int score = Integer.MAX_VALUE;
			TreeSet<Integer> target = Roops.block(b).get(0);
			String answer = null;
			for (byte i = 0; i < b.b.length - 1; i++) {
				if (b.fixed[i]) {
					continue;
				}
				Roop[] baseGroups = selectMinBase(b, threeRoops, i, target);
				if (baseGroups == null) {
					continue;
				}
				Board b2 = b.clone();
				List<Integer> notContains = notContainsPoint(b, baseGroups,
						target);
				if (notContains.size() >= 1) {
					moveGaol(b2, notContains);
				}
				resolve(b2, threeRoops, i, baseGroups);
				if (score > b2.counter) {
					score = b2.counter;
					answer = b2.history();
				}
			}
			if (answer == null) {
				throw new IllegalStateException();
			}
			return answer;
		}
		if (findBridgePoint.size() == 1) {
			int score = Integer.MAX_VALUE;

			int bridePoint = findBridgePoint.get(0);
			Board b2 = b.clone();
			b2.fixed[bridePoint] = true;
			List<TreeSet<Integer>> set = Roops.block(b2);
			TreeSet<Integer> first = set.get(0);
			TreeSet<Integer> second = set.get(0);
			String answer = null;
			for (int f : first) {
				int localScore = 0;
				for (int s : second) {
					Roop[] list = selectMinBase(b, threeRoops, (byte) f, first);
					if (list == null) {
						continue;
					}
					Roop[] list2 = selectMinBase(b, threeRoops, (byte) s,
							second);
					if (list2 == null) {
						continue;
					}

					List<Integer> notContains = notContainsPoint(b, list, first);
					if (notContains.size() >= 1) {
						moveGaol(b, notContains);
					}
					notContains = notContainsPoint(b, list2, second);
					if (notContains.size() >= 1) {
						moveGaol(b, notContains);
					}
					boolean resolve = resolve(b, threeRoops, (byte) f, list);
					if (resolve == false) {
						continue;
					}
					resolve = resolve(b, threeRoops, (byte) s, list2);
					if (resolve == false) {
						continue;
					}

					if (b.counter < score) {
						score = localScore;
						answer = b.history();
					}
				}
			}
			return answer;
		}
		throw new IllegalStateException();
	}

	private static void moveGaol(Board b, List<Integer> notContains) {
		if (DEBUG) {
			System.out.println(notContains);
		}
		for (int point : notContains) {
			if (b.g[point] != b.b[point]) {
				b.move(b.g[point], point);
				if (DEBUG) {
					b.print();
				}
			}
			b.fixed[point] = true;
		}
		for (int point : notContains) {
			b.fixed[point] = false;
		}
	}

	private static Roop[] selectMinBase(Board b, List<Roop> threeRoops, byte i,
			TreeSet<Integer> target) {
		Roop[] tmp = Roops.makeMinimumBase(b, threeRoops, i);
		if (tmp == null) {
			return null;
		}
		Board board = b.clone();
		List<Integer> notContains = notContainsPoint(board, tmp, target);
		if (notContains.size() >= 1) {
			try {
				for (int point : notContains) {
					if (!target.contains(point)) {
						continue;
					}
					if (board.g[point] != board.b[point]) {
						board.move(b.g[point], point);
						if (DEBUG) {
							board.print();
						}
					}
					board.fixed[point] = true;
				}
			} catch (IllegalStateException e) {
				if (DEBUG) {
					e.printStackTrace(System.out);
				}
				return null;
			}
			for (int point : notContains) {
				board.fixed[point] = false;
			}
		}
		return tmp;
	}

	private static boolean resolve(Board b, List<Roop> threeRoops, byte base,
			Roop[] baseGroup) {
		List<RoopPair> pairList = new ArrayList<RoopPair>();
		for (Roop group3 : baseGroup) {
			if (group3.constains(base)) {
				pairList.add(new RoopPair(group3, base));
			} else {
				// ŒÇ—§“_
				RoopPair pair = new RoopPair(group3, group3.points[0]);
				if (!pair.isGaoled(b)) {
					pair.group.f(b);
				}
				if (!pair.isGaoled(b)) {
					pair.group.f(b);
				}
				if (!pair.isGaoled(b)) {
					b.print();
					System.out.println("^^^^^^^^^^^^^^");
					System.out.println("base " + base + " group " + pair.group);
					System.out.println("^^^^^^^^^^^^^^");
					for (Roop bb : baseGroup) {
						System.out.println(bb);
					}
					System.out.println("^^^^^^^^^^^^^^");
					for (Roop bb : threeRoops) {
						System.out.println(bb);
					}
					throw new IllegalStateException();
				}
			}
			if (DEBUG)
				System.out.println(group3);
		}
		if (DEBUG) {
			System.out.println("-----");
		}
		{
			for (RoopPair pair : pairList) {
				if (DEBUG) {
					System.out.println("-----------------");
					System.out.println("-------" + pair.group + "----------");
					System.out.println("-----------------");
					b.print();
				}
				pair.checked = true;
				if (pair.isGaoled(b)) {
					continue;
				}
				if (pair.firstIsSecond(b) && pair.secondIsFirst(b)) {
					pair.group.f_1(b);
					for (RoopPair p : pairList) {
						if (p.checked == false) {
							p.group.f_1(b);
							break;
						}
					}
				}
				if (pair.isFirstGaoled(b)) {
					if (pair.secondIsBase(b)) {
						for (RoopPair p : pairList) {
							if (p.checked == false) {
								p.group.f_1(b);
								break;
							}
						}
					}
					if (pair.secondIsBase(b)) {
						throw new IllegalStateException();
					}
					pair.group.f_1(b);
				}
				if (pair.isSecondGaoled(b)) {
					if (pair.firstIsBase(b)) {
						for (RoopPair p : pairList) {
							if (p.checked == false) {
								p.group.f_1(b);
								break;
							}
						}
					}
					if (pair.firstIsBase(b)) {
						throw new IllegalStateException();
					}
					pair.group.f(b);
				}
				if (DEBUG) {
					b.print();
				}
				if (pair.firstIsSecond(b) && pair.secondIsFirst(b)) {
					throw new IllegalStateException("‚Ë‚¶‚ê");
				}
				if (!pair.firstIsSecond(b)) {
					int fpos = b.find(b.g[pair.first]);
					if (pair.base != fpos) {
						for (RoopPair p2 : pairList) {
							if (p2.first == fpos) {
								p2.group.f(b);
								if (DEBUG) {
									b.print();
								}
								break;
							}
							if (p2.second == fpos) {
								p2.group.f_1(b);
								if (DEBUG) {
									b.print();
								}
								break;
							}
						}
					}
					pair.group.f(b);
					if (DEBUG) {
						b.print();
					}
				}
				if (!pair.secondIsFirst(b)) {
					int fpos = b.find(b.g[pair.second]);
					if (pair.base != fpos) {
						for (RoopPair p2 : pairList) {
							if (p2.first == fpos) {
								p2.group.f(b);
								if (DEBUG) {
									b.print();
								}
								break;
							}
							if (p2.second == fpos) {
								p2.group.f_1(b);
								if (DEBUG) {
									b.print();
								}
								break;
							}
						}
					}
					pair.group.f(b);
					if (DEBUG) {
						b.print();
					}
				}
				if (DEBUG) {
					b.print();
				}
				if (!pair.isGaoled(b)) {
					for (RoopPair r : pairList) {
						System.out.println(r.group);
					}
					System.out.println(pairList.size());
					b.print();
					return false;
					// throw new IllegalStateException(base + " " + pair.group
					// + "");
				}
			}
		}
		return true;
	}

	private static List<Integer> notContainsPoint(Board b, Roop[] list,
			TreeSet<Integer> target) {
		boolean[] panels = new boolean[b.b.length];
		System.arraycopy(b.fixed, 0, panels, 0, panels.length);
		panels[b.home()] = true;
		for (Roop roop : list) {
			for (int r : roop.points) {
				panels[r] = true;
			}
		}
		List<Integer> output = new ArrayList<Integer>();
		for (int i = 0; i < panels.length; i++) {
			if (panels[i] == false && target.contains(i)) {
				output.add(i);
			}
		}
		return output;
	}

}
