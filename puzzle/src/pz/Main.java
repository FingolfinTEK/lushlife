package pz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
	public static void main(String[] args) throws Exception, IOException {
		main6(args);
	}

	public static void main6(String[] args) throws FileNotFoundException,
			IOException {
		Puzzle pz = new Puzzle();
		pz.read(new FileInputStream("problems.txt"), 5000);
		int counter = 0;
		try {
			PrintStream answer = new PrintStream("output.txt");
			for (Board b : pz.boards)
			// Board b = pz.boards.get(0);
			{
				counter++;
				// if (counter < 184) {
				// continue;
				// }
				String command = Resolver.resolve(b);
				answer.println(command);
				pz.count(command);
				System.out.println(counter + " " + command.length() + " ");
				System.out.println(command);
				System.out.println(pz);
			}
		} finally {
			System.out.println(counter);
			System.out.println(pz);
		}

	}

	public static Puzzle readFile() throws IOException, FileNotFoundException {
		Puzzle pz = new Puzzle();
		pz.read(new FileInputStream("problems.txt"), 10);
		return pz;
	}
}
