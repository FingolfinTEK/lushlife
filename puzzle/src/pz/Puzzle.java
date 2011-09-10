package pz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Puzzle {

	int LX, RX, UX, DX;
	int L, R, U, D;
	int N;
	List<Board> boards = new ArrayList<Board>();

	public void read(InputStream in, int size) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String[] first = br.readLine().split(" ");
		this.LX = Integer.parseInt(first[0]);
		this.RX = Integer.parseInt(first[1]);
		this.UX = Integer.parseInt(first[2]);
		this.DX = Integer.parseInt(first[3]);
		this.N = Integer.parseInt(br.readLine());

		try {
			for (String s = br.readLine(); s != null; s = br.readLine()) {
				size--;
				if (size < 0) {
					break;
				}
				Board b = new Board(this);
				b.init(s);
				boards.add(b);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return "L " + L + "/" + LX + " R " + R + "/" + RX + " U " + U + "/"
				+ UX + " D " + D + "/" + DX;
	}

	public void count(String command) {
		for (char c : command.toCharArray()) {
			switch (c) {
			case 'L':
				L++;
				break;
			case 'U':
				U++;
				break;
			case 'R':
				R++;
				break;
			case 'D':
				D++;
				break;
			}
		}
	}
}
