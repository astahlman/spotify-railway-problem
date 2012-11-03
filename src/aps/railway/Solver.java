package aps.railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Solver {
	final int A = 0;
	final int B = 1;
	final int C = 2; 
	
	int numVertices, numEdges;
	Scanner scanner;
	
	public Solver(Scanner scanIn) {
		scanner = scanIn;
		numVertices = scanner.nextInt();
		numEdges = scanner.nextInt();
		scanner.nextLine();
	}
	
	public String solve() {
		Road start = buildGraph(scanner);
		Road end = bfsSearch(start);
		Road current = end;
		if (current == null) {
			return "Impossible";
		} else {
			LinkedList<Road> path = new LinkedList<Road>();
			while (current != null) {
				path.add(0, current);
				current = current.getParent();
			}
			
			StringBuilder pathString = new StringBuilder();
			StringBuilder switchString = 
					new StringBuilder(StringUtils.repeat("B", numVertices));
			
			for (Road r : path) {
				int junction = r.getj1() / 3;
				if (r.getj1() % 3 == C) {
					switchString.setCharAt(junction, 'C');
				}
				pathString.append(r + "->");
			}
			
			pathString = pathString.delete(pathString.length() - 2, pathString.length());
			System.out.println("Path: " + pathString.toString());
			return switchString.toString();
		}
	}
	
	public Road buildGraph(Scanner scan) {
		HashMap<String, Road> roads = new HashMap<String, Road>();
		Scanner lineScan;
		String s1, s2;
		while (scan.hasNextLine()) {
			lineScan = new Scanner(scan.nextLine());
			s1 = lineScan.next();
			s2 = lineScan.next();
			roads.put(s1, new Road(s1, s2));
			roads.put(s2, new Road(s2, s1));
		}
		
		for (int i = 0; i < numVertices * 3; i++) {
			Road fromStart = roads.get(Road.junctionForIndex(i));
			if (fromStart == null) {
				continue;
			} else if (fromStart.entersSwitch()) {
				Road fromDestB = roads.get(Road.junctionForIndex(fromStart.getj2() + B));
				Road fromDestC = roads.get(Road.junctionForIndex(fromStart.getj2() + C));
				fromStart.addAdj(fromDestB);
				fromStart.addAdj(fromDestC);
			} else {
				int indexA = fromStart.getj2() - (fromStart.getj2() % 3);
				fromStart.addAdj(roads.get(Road.junctionForIndex(indexA)));
			}
		}
		
		Road startReverse = roads.get("1A");
		return roads.get(Road.junctionForIndex(startReverse.getj2()));
	}
	
	public Road bfsSearch(Road r) {
		Queue<Road> Q = new LinkedList<Road>();
		for (Road begin : r.getAdj()) {
			Q.add(begin);
		}
		ArrayList<Road> adj;
		Road current;
		while (!Q.isEmpty()) {
			current = Q.poll();
			if (r == current) {
				return current;
			}
			adj = current.getAdj();
			for (Road n : adj) {
				if (!n.isVisited()) {
					Q.add(n);
					n.setVisited(true);
					n.setParent(current);
				}
			}
		}
		return null;
	}
}