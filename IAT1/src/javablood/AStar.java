package javablood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javablood.Environment.Tile;

public class AStar implements Algorithm {
	
	public Point[] getPath(Point start, Point objective, Environment environment) {
		Point[] points = environment.getAsPoints();
		start = Point.findPoint(start, points);
		objective = Point.findPoint(objective, points);
		//Updating references - eh, might be useless
		
		PriorityQueue<PriorityPoint> frontier = new PriorityQueue<PriorityPoint>();
		
		frontier.add(new PriorityPoint(start, 0));
		
		HashMap<Point, Double> costSoFar = new HashMap<Point, Double>();
		HashMap<Point, Point> cameFrom = new HashMap<Point, Point>();
		
		for(Point point: points){
			costSoFar.put(point, Double.MAX_VALUE);
			cameFrom.put(point, null);
		}
			
		costSoFar.put(start, (double) 0);
		
		while (!frontier.isEmpty()) {
			PriorityPoint current = frontier.poll();
			
			if(current.point.equals(objective)) break;
			
			for (Point next: getAcessableTiles(current.point, points, environment)) {
				
				double newCost = costSoFar.get(current.point) + costToClosest(current.point, next);
				
				if (newCost < costSoFar.get(next) ) {
					
					costSoFar.put(next, newCost);
					double priority = newCost + costToObjective(next, objective);
					
					frontier.add(new PriorityPoint(next, priority));
					cameFrom.put(next, current.point);
				}
				
			}
		}
		
		//for (Node node: cameFrom)
		
		return tracePath(start, objective, cameFrom);
	}
	
	private Point[] getAcessableTiles(Point start, Point[] referencePoints, Environment environment) {
		List<Point> acessableTiles = new ArrayList<Point>();		
		
		
		int height = environment.field.length;
		int width = environment.field[0].length;
		
		for (int y = start.y - 1; y <= start.y + 1; y++) {
			for(int x = start.x - 1; x <= start.x + 1; x++) {
				Point point = new Point(x, y);
				
				if (!(point.isInsideField(environment.field))) continue;	//Check if it's inside the matrix
				if (point.equals(start)) continue;								//Check if it's the tile we're checking
				if (environment.field[y][x] == Tile.WALL) continue;				//Check if it's a wall
				if (environment.field[y][x] == Tile.TRASHCAN) continue;
				
				acessableTiles.add(Point.findPoint(new Point(x, y), referencePoints));																			
			}
		}
		
		Point[] result = new Point[acessableTiles.size()];
		return acessableTiles.toArray(result);
	}

	private double costToClosest(Point start, Point point) {
		double cost = Euclidson.calculateEuclideanDistance(start, point);
		return cost;
	}
	// Both methods are pretty much the same, but we've kept them separate in case we want to change the heuristics later
	private double costToObjective(Point point, Point objective) {
		double cost = Euclidson.calculateEuclideanDistance(point, objective); 
		return cost;
	}
	
	private Point[] tracePath(Point start, Point objective, HashMap<Point, Point> parents){
		ArrayList<Point> path = new ArrayList<>();
		Point currentPoint = objective;
		while(parents.get(currentPoint) != null && !currentPoint.equals(start)){
			path.add(0, currentPoint);
			currentPoint = parents.get(currentPoint);
		}
		Point[] result = new Point[path.size()];
		return (path.toArray(result));
	}
	
	private class PriorityPoint implements Comparable<PriorityPoint>{
		private Point point;
		private double priority;
		public PriorityPoint(Point point, double value) {
			this.point = point;
			this.priority = value;
		}
		@Override
		public int compareTo(PriorityPoint node) {
			int res = 0;
			if (this.priority == node.priority) res = 0;
			else if(this.priority > node.priority) res = 1;
			else res = -1;
			
			return res;
		}
	}

}
