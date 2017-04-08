package javablood;

public class Euclidson {

	public static double calculateEuclideanDistance(Point a, Point b) {
		return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
	}
	
}
