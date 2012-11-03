package aps.railway;
import java.util.ArrayList;

import org.apache.commons.lang3.builder.*;

public class Road {
	protected int j1;
	protected int j2;
	protected ArrayList<Road> adj = new ArrayList<Road>();
	protected Road parent;
	protected boolean visited = false;

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Road getParent() {
		return parent;
	}

	public void setParent(Road parent) {
		this.parent = parent;
	}

	public Road(String junction1, String junction2) {
		this(indexForJunction(junction1), indexForJunction(junction2));
	}
	
	public Road(int junction1, int junction2) {
		j1 = junction1;
		j2 = junction2;
	}
	
	public int getj1() {
		return j1;
	}

	public void setj1(int j1) {
		this.j1 = j1;
	}

	public int getj2() {
		return j2;
	}

	public void setj2(int j2) {
		this.j2 = j2;
	}

	public ArrayList<Road> getAdj() {
		return adj;
	}

	public void addAdj(Road r) {
		if (r != null) {
			adj.add(r);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj == this) {
			return false;
		} else if (obj.getClass() != getClass()) {
			return false;
		}
		
		Road other = (Road) obj;
		return new EqualsBuilder().
				append(j1, other.j1).
				append(j2, other.j2).
				isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 31).
				append(j1).
				append(j2).
				toHashCode();
	}
	
	@Override
	public String toString() {
		return junctionForIndex(j1) + junctionForIndex(j2);
	}
	
	public boolean entersSwitch() {
		return (j2 % 3) == 0;
	}
	
	public static int indexForJunction(String s) {
		int num = Integer.parseInt(s.substring(0, s.length() - 1));
		char port = s.charAt(s.length() - 1);
		int portNum = port - 'A';
		return ((num - 1) * 3) + portNum;
	}
	
	public static String junctionForIndex(int j) {
		int base = (j / 3) + 1;
		char port = (char)((j % 3) + (int)'A');
		return Integer.toString(base) + port;
	}
}
