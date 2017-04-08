package javablood;

import java.util.Set;

public interface Algorithm {
	
	public Point calculateNextMove(Point start, Point end, Set<Point> previousTiles, Environment environment);
	
}
