package javablood;

public class Point { 
	int x, y; 
	
	Point (int x, int y) { 
		this.x = x; 
		this.y = y;
	}
	
	public boolean equals(Point point) {
		//if (this.getClass() != obj.getClass()) return false;
		return this.x == point.x && this.y == point.y;
	}
	
	public String toString() {
		return x + " " + y;
	}
	
}