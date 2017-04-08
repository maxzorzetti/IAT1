package javablood;

import javablood.Environment.Tile;

public class Trashbot {
		
	private Environment environment;
	
	private Algorithm algorithm;
	
	private Point currentLocation;
	private Point lastLocation;
	
	public Trashbot(Environment environment, Algorithm algorithm) {
		this.environment = environment;
		this.algorithm = algorithm;
		this.currentLocation = new Point(0, 0);
		this.lastLocation = new Point(0, 0);
	}
	
	public void cleanEnvironment() {
		
		while(true) {
			Tile currentTile = look();
			
			switch(currentTile) {
				case EMPTY: ; break;
				case JUNK: clean(); break;
				case TRASHCAN:
				case WALL:
				default:
				System.out.println("Uh oh");
			}
			
		}
		
	}
	
	private void clean() {
		this.environment.field[currentLocation.y][currentLocation.x] = Tile.EMPTY;
		
		TrashCan closestTrashCan = findClosestTrashCan();
		
	}
	
	private TrashCan findClosestTrashCan() {	// TODO Change to include a 1-cell radius around the trashcan when searching
		TrashCan closestTrashCan = environment.trashCans[0];
		double closestTrashCanDistance = Euclidson.calculateEuclideanDistance(currentLocation, closestTrashCan.getCoordinates());
		
		for (TrashCan trashCan: environment.trashCans) {
			double distance = Euclidson.calculateEuclideanDistance(currentLocation, trashCan.getCoordinates());
			
			if (distance > closestTrashCanDistance) {
				closestTrashCan = trashCan;
				closestTrashCanDistance = distance;
			}
		}
		
		return closestTrashCan;
	}

	private Environment.Tile look() {
		return environment.field[currentLocation.y][currentLocation.x];
	}
}
