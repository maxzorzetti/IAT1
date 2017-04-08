package javablood;

import java.util.ArrayList;
import java.util.List;

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
		
		for (int y = start.y - 1; y < start.y + 1; y++) {
			for(int x = start.x - 1; x < start.x + 1; x++) {
				if (x < environment.field[0].length && y < environment.field.length && (x != start.x && y != start.y)) {
					acessableTiles.add(new Point(x, y));
				}
			}
		}
		
		return (Point[]) acessableTiles.toArray();
	}

	private double costToNearest(Point start, Point point) {
		double cost = Math.abs((point.x - point.x) + (point.y - point.y));
		return cost;
	}
	// Both methods are pretty much the same, but I've kept them separate if I want to change the heuristics later
	private double costToObjective(Point point, Point objective) {
		double cost = Math.abs((point.x - objective.x) + (point.y - objective.y)); 
		return cost;
	}

}
