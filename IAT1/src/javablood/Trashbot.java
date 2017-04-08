package javablood;

import java.util.Scanner;

import javablood.Environment.Tile;

public class Trashbot {
		
	private Environment environment;
	
	private Algorithm algorithm;
	
	private int junkCapacity;
	private int junkHeld;
	
	private Point currentLocation;
	private Point lastLocation;
	
	public Trashbot(Environment environment, Algorithm algorithm, int junkCapacity) {
		this.environment = environment;
		this.algorithm = algorithm;
		this.junkCapacity = junkCapacity;
		this.junkHeld = 0;
		this.currentLocation = new Point(0, 0);
		this.lastLocation = new Point(0, 0);
	}
	
	public void cleanEnvironment() {
		
		while (true) {
			
			Tile currentTile = look();
			
			switch(currentTile) {
				case EMPTY: ; break;
				case JUNK: clean(); break;
				case TRASHCAN:
				case WALL:
				default:
				System.out.println("Uh oh");
			}
			
			lookForJunk();
		}
		
	}
	
	private void lookForJunk() {
		// TODO Auto-generated method stub
		currentLocation = new Point(currentLocation.x + 1, currentLocation.y + 0); // Move somehow
		lastLocation = currentLocation;
	}

	private void clean() {
		Scanner in = new Scanner(System.in);
		
		this.environment.field[currentLocation.y][currentLocation.x] = Tile.EMPTY;
		junkHeld++;
		if( junkHeld < junkCapacity) return;
		
		
		TrashCan closestTrashCan = findClosestTrashCan();
		//Go to trashcan
		Point trashCanLocation = closestTrashCan.getCoordinates();
		System.out.println(this);

		moveTo(trashCanLocation);
		
		//if(!closestTrashCan.isFull()) {
			closestTrashCan.addTrash();
			junkHeld = 0;
		//} else {
			// cry
		//}
		moveTo(lastLocation);
		
	}
	
	private void moveTo(Point point) {
		Scanner in = new Scanner(System.in);
		while (!currentLocation.equals(point)) {
			currentLocation = algorithm.calculateNextMove(currentLocation, point, environment);
			in.nextLine();
			System.out.println(this);
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
		System.out.println(currentLocation.y + " " + currentLocation.x);
		return environment.field[currentLocation.y][currentLocation.x];
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
