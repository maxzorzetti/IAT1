package javablood;

import java.util.Scanner;

import javablood.Environment.Tile;

public class Trashbot {
	
	private Scanner in = new Scanner(System.in); // Not proud of this
	public boolean pushToPrint = true;
	public boolean enablePrint = true;
	public boolean cleanOnPrint = true;
	
	private Environment environment;
	private boolean hasLookedAtAllTiles;
	
	private Algorithm algorithm;
	
	private int junkCapacity;
	private int junkHeld;
	
	private boolean direction;
	
	private Point currentLocation;
	private Point lastLocation;
	
	public Trashbot(Environment environment, Algorithm algorithm, int junkCapacity) {
		this.environment = environment;
		this.hasLookedAtAllTiles = false;
		this.algorithm = algorithm;
		this.junkCapacity = junkCapacity;
		this.junkHeld = 0;
		this.direction = true;
		this.currentLocation = new Point(0, 0);
		this.lastLocation = new Point(0, 0);
	}
	
	public void cleanEnvironment() {
		
		showStatus();
		
		while (!hasLookedAtAllTiles) {
			
			Tile currentTile = look();
			
			if(currentTile == Tile.JUNK) clean();
			
//			switch(currentTile) {
//				case EMPTY: ; break;
//				case JUNK: clean(); break;
//				case TRASHCAN:
			
//				case WALL:
//				default:
//			}
//			
			lookForJunk();
		}
		
		showStatus();
		
	}
	
	private void lookForJunk() {
		Point move = new Point(currentLocation.x, currentLocation.y);
		int horizontalMovement = direction? 1 : -1;	
		move.x += horizontalMovement; 
		
		while (!hasLookedAtAllTiles) {
			horizontalMovement = direction? 1 : -1;
			
			while (move.isInsideField(environment.field) && (environment.field[move.y][move.x] == Tile.WALL || environment.field[move.y][move.x] == Tile.TRASHCAN)) {
				move.x += horizontalMovement;
			}
			
			if(move.isInsideField(environment.field)) {
				moveTo(move);
				lastLocation = currentLocation;
				return;
			}
			
			direction = !direction;	
			move.x -= horizontalMovement;
			move.y += 1;
			
			if(!move.isInsideField(environment.field)) hasLookedAtAllTiles = true;
		}

		
		
//		
//		Point newLocation = new Point(currentLocation.x + horizontalMovement, currentLocation.y);	//Move horizontally		
//		if(newLocation.isInsideField(environment.field)) {
//			while(environment.field[newLocation.y][newLocation.x] == Tile.WALL) newLocation.x += horizontalMovement;
//		} else {
//			direction = !direction;	//Change horizontal direction
//			newLocation = new Point(currentLocation.x, currentLocation.y + 1);			//Move vertically
//			//Moved vertically and stepped outside the field = all tiles have been cleaned
//			if(!newLocation.isInsideField(environment.field)){
//				lookedAtAllTiles = true;	
//				return;
//			}
//		}			
//		moveTo(newLocation);
//		lastLocation = currentLocation;
		
	}

	private void clean() {		
		this.environment.field[currentLocation.y][currentLocation.x] = Tile.EMPTY;
		junkHeld++;
		if( junkHeld < junkCapacity) return;
		
		
		// Go to closest trash can
		Object[] imSorry = findClosestTrashCan();
		TrashCan closestTrashCan = (TrashCan) imSorry[0];
		Point trashCanLocation = (Point) imSorry[1];
		moveTo(trashCanLocation);
		
		//Empty junk repository on trashcan
		closestTrashCan.addTrash();
		junkHeld = 0;

		//Go back to the previous location
		moveTo(lastLocation);
		
	}
	
	private void moveTo(Point point) {		
		Point[] path = algorithm.getPath(currentLocation, point, environment);

		for (Point pointa: path) {
			System.out.print(pointa + " ");
		}
		System.out.println();
		
		for (Point pathPoint: path) {
			showStatus();
			currentLocation = pathPoint;
		}

	}
	

//	private void moveTo(Point point) {							//Old implementation
//		//HashSet<Point> visitedTiles = new HashSet<Point>();
//		
//		while (!currentLocation.equals(point)) {
//			//currentLocation = algorithm.calculateNextMove(currentLocation, point, visitedTiles, environment);
//			//visitedTiles.add(currentLocation);
//			showStatus();
//		}
//	}
	
	private Object[] findClosestTrashCan() {
		TrashCan closestTrashCan = environment.trashCans[0];
		Point closestPoint = environment.trashCans[0].getWalkableAdjacentTiles()[0];
		double closestPointDistance = Euclidson.calculateEuclideanDistance(currentLocation, closestPoint);
		
		for (TrashCan trashCan: environment.trashCans) {
			
			Point[] adjacentTiles = trashCan.getWalkableAdjacentTiles();
			
			for (Point point: adjacentTiles) {
				double distance = Euclidson.calculateEuclideanDistance(currentLocation, point);

				if (distance < closestPointDistance) {
					closestTrashCan = trashCan;
					closestPoint = point;
					closestPointDistance = distance;
				}
			}
			
		}
		Object[] sorryForThis = new Object[2];
		sorryForThis[0] = closestTrashCan;
		sorryForThis[1] = closestPoint;
		return sorryForThis;
	}

	private Environment.Tile look() {
		return environment.field[currentLocation.y][currentLocation.x];
	}
	
	private void showStatus() {
		//if (cleanOnPrint) System.out.println("\f");
		if (enablePrint) System.out.println(this);
		if (pushToPrint) in.nextLine();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Junk Held: " + junkHeld + "/" + junkCapacity + "\n");
		
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
				case JUNK: tile = "."; break;
				case TRASHCAN: tile = "V"; break;
				}
				
				sb.append(tile + " ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
