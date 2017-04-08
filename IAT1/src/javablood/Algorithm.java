package javablood;

public interface Algorithm {
	
	public Point calculateNextMove(Point start, Point end, Point[] previousTiles, Environment environment);
	
}
