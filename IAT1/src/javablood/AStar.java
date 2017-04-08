package javablood;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javablood.Environment.Tile;

public class AStar implements Algorithm {
	
	

	@Override
	public Point calculateNextMove(Point start, Point objective, Set<Point> previousTiles, Environment environment) {
		Point[] acessableTiles = getAcessableTiles(start, previousTiles, environment);
		
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

	private Point[] getAcessableTiles(Point start, Set<Point> previousTiles, Environment environment) {
		List<Point> acessableTiles = new ArrayList<Point>();		
		
		int height = environment.field.length;
		int width = environment.field[0].length;
		
		for (int y = start.y - 1; y <= start.y + 1; y++) {
			for(int x = start.x - 1; x <= start.x + 1; x++) {
				Point point = new Point(x, y);
				
				if (!(x < width && y < height && x >= 0 && y >= 0)) continue;	//Check if it's inside the matrix
				if (point.equals(start)) continue;								//Check if it's the tile we're checking
				if (environment.field[y][x] == Tile.WALL) continue;				//Check if it's a wall
				if (previousTiles.contains(point)) continue;					//Check if it has already been stepped on
				
				acessableTiles.add(new Point(x, y));																			
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
