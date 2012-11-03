package aps.railway;
import aps.railway.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestDriver {
	public static void main(String args[]) throws FileNotFoundException {
		String[] testFiles = { "src/testinput1", "src/testinput2" };
		String[] expected = { "BBBBBBBBCC", "Impossible" };
		Scanner fileScan;
		Solver solver;
		for (int i = 0; i < testFiles.length; i++) {
			fileScan = new Scanner(new File(testFiles[i]));
			solver = new Solver(fileScan);
			String result = solver.solve();
			if (!result.equals(expected[i])) {
				System.out.println((i + 1) + " - Error! Expected: " + expected[i]);
				System.out.println("Actual: " + result);
			} else {
				System.out.println((i + 1) + " - Passed! " + result);
			}
		}
	}
}
