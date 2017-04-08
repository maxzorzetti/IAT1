package javablood;

import java.util.ArrayList;
import java.util.List;

import javablood.Environment.Tile;

public class AStar implements Algorithm {

	@Override
	public Point calculateNextMove(Point start, Point objective, Environment environment) {
		Point[] acessableTiles = getAcessableTiles(start, environment);
		
		Point bestPoint = null;
		double bestScore = Double.MAX_VALUE;
		for (Point point: acessableTiles ) {
			double pointScore = costToNearest(start, point) + costToObjective(point, objective);
			if (pointScore < bestScore) {
				bestScore = pointScore;
				bestPoint = point;
			}
		}
		
		return bestPoint;
	}

	private Point[] getAcessableTiles(Point start, Environment environment) {
		List<Point> acessableTiles = new ArrayList<Point>();		
		
		for (int y = start.y - 1; y <= start.y + 1; y++) {
			for(int x = start.x - 1; x <= start.x + 1; x++) {
				
				if (x < environment.field[0].length && y < environment.field.length) {	//TODO REFACTOR
					if (x >= 0 && y >= 0) {
						if (!(x == start.x && y == start.y)) {
							if (environment.field[y][x] != Tile.WALL) {
								acessableTiles.add(new Point(x, y));															
							}
						}
					}
				}
				
			}
		}
		
		Point[] result = new Point[acessableTiles.size()];
		return acessableTiles.toArray(result);
	}

	private double costToNearest(Point start, Point point) {
		double cost = Euclidson.calculateEuclideanDistance(start, point);
		return cost;
	}
	// Both methods are pretty much the same, but I've kept them separate if I want to change the heuristics later
	private double costToObjective(Point point, Point objective) {
		double cost = Euclidson.calculateEuclideanDistance(point, objective); 
		return cost;
	}

}
