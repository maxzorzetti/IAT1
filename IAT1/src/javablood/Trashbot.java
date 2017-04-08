package javablood;

import java.util.HashSet;
import java.util.Scanner;

import javablood.Environment.Tile;

public class Trashbot {
	
	private Scanner in = new Scanner(System.in); // Not proud of this
	public boolean pushToPrint = true;
	public  boolean cleanOnPrint = true;
	
	private Environment environment;
	private boolean lookedAtAllTiles;
	
	private Algorithm algorithm;
	
	private int junkCapacity;
	private int junkHeld;
	
	private boolean direction;
	
	private Point currentLocation;
	private Point lastLocation;
	
	public Trashbot(Environment environment, Algorithm algorithm, int junkCapacity) {
		this.environment = environment;
		this.lookedAtAllTiles = false;
		this.algorithm = algorithm;
		this.junkCapacity = junkCapacity;
		this.junkHeld = 0;
		this.direction = true;
		this.currentLocation = new Point(0, 0);
		this.lastLocation = new Point(0, 0);
	}
	
	public void cleanEnvironment() {
		
		while (!lookedAtAllTiles) {
			
			Tile currentTile = look();
			
			switch(currentTile) {
				case EMPTY: ; break;
				case JUNK: clean(); break;
				case TRASHCAN:
				case WALL:
				default:
			}
			
			lookForJunk();
		}
		
	}
	
	private void lookForJunk() {
		int xMovement = direction? 1 : -1;
		
		Point newLocation = new Point(currentLocation.x + xMovement, currentLocation.y);	//Move horizontally
		
		if(newLocation.isInsideField(environment.field)) {
			currentLocation = newLocation;
		} else {
			direction = !direction;	//Change horizontal direction
			currentLocation = new Point(currentLocation.x, currentLocation.y + 1);			//Move vertically
		
			//Moved vertically and stepped outside the field = all tiles have been cleaned
			if(!currentLocation.isInsideField(environment.field)) lookedAtAllTiles = true;	
		}
			
		lastLocation = currentLocation;
		
		showStatus();
	}

	private void clean() {		
		this.environment.field[currentLocation.y][currentLocation.x] = Tile.EMPTY;
		junkHeld++;
		if( junkHeld < junkCapacity) return;
		
		
		// Go to closest trash can
		TrashCan closestTrashCan = findClosestTrashCan();
		Point trashCanLocation = closestTrashCan.getCoordinates();
		moveTo(trashCanLocation);
		
		//Empty junk repository on trashcan
		closestTrashCan.addTrash();
		junkHeld = 0;

		//Go back to the previous location
		moveTo(lastLocation);
		
	}
	
	private void moveTo(Point point) {		
		HashSet<Point> visitedTiles = new HashSet<Point>();
		
		while (!currentLocation.equals(point)) {
			currentLocation = algorithm.calculateNextMove(currentLocation, point, visitedTiles, environment);
			visitedTiles.add(currentLocation);
			showStatus();
		}
	}
	
	private TrashCan findClosestTrashCan() {	// TODO Change to include a 1-cell radius around the trashcan when searching
		TrashCan closestTrashCan = environment.trashCans[0];
		double closestTrashCanDistance = Euclidson.calculateEuclideanDistance(currentLocation, closestTrashCan.getCoordinates());
		
		for (TrashCan trashCan: environment.trashCans) {
			double distance = Euclidson.calculateEuclideanDistance(currentLocation, trashCan.getCoordinates());
			
			if (distance < closestTrashCanDistance) {
				closestTrashCan = trashCan;
				closestTrashCanDistance = distance;
			}
		}
		
		return closestTrashCan;
	}

	private Environment.Tile look() {
		return environment.field[currentLocation.y][currentLocation.x];
	}
	
	private void showStatus() {
		//if (cleanOnPrint) System.out.println("\f");
		System.out.println(this);
		if (pushToPrint) in.nextLine();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < environment.field.length; y++) {
			for (int x = 0; x < environment.field[0].length; x++) {
				String tile;
				
				if(new Point(x, y).equals(currentLocation)) {
					sb.append("X ");
					continue;
				}
				
				switch(environment.field[y][x]) {
				default:
				case EMPTY: tile = " "; break;
				case WALL: tile = "W"; break;
				case JUNK: tile = "·"; break;
				case TRASHCAN: tile = "V"; break;
				}
				
				sb.append(tile + " ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
