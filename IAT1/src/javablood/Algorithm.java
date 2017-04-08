package javablood;

public interface Algorithm {
	
	public class Point { 
		int x; 
		int y; 
		Point (int x, int y) { this.x = x; this.y = y; }
	};
	
	public Point calculateNextMove(Point start, Point end, Environment environment);
	
}
