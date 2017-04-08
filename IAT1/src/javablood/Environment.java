package javablood;

import java.util.Random;

public class Environment {
	
	public enum Tile { EMPTY, TRASHCAN, JUNK, WALL, BOT };
	
	protected Tile[][] field;
	protected TrashCan[] trashCans;
	
	private int totalWalls;
	private int totaltrashCans;
	
	public Environment(int size, int trashCans){			
		createField(size);
		buildWalls();
		generatetrashCans(trashCans);
		generateJunk();
		
		System.out.println();
	}
	
	private void createField(int size) {
		this.field = new Tile[size][size];
		
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[0].length; x++) {
				field[y][x] = Tile.EMPTY;
			}
		}
		System.out.println("Field size is " + field.length + "x" + field[0].length);
		System.out.println("Total tiles: " + field.length * field[0].length);
	}
	
	private void generatetrashCans(int trashCans) {
		totaltrashCans = trashCans;
		this.trashCans = new TrashCan[trashCans];
		int trashCansIndex = 0;
		int thrashcanAreaHeight = field.length * 2 / 3;
		int thrashcanAreaWidth = field.length / 3;
		
		Random rand = new Random();
		
		//Generate trash cans on the left
		int x = 0;
		int y = 2*field.length/12;		
		int lefttrashCans = (int) Math.ceil(trashCans/2.0);
		
		//Final coordinates of a trash can (x and y + offset)
		int xTrashCan, yTrashCan;
		
		while(lefttrashCans > 0) {
			int xOffset = rand.nextInt(thrashcanAreaWidth);
			int yOffset = rand.nextInt(thrashcanAreaHeight);
			
			xTrashCan = x + xOffset;
			yTrashCan = y + yOffset;
			
			if (field[yTrashCan][xTrashCan] == Tile.EMPTY) {
				field[yTrashCan][xTrashCan] = Tile.TRASHCAN;
				//trash can's capacity is zero because it's infinite
				this.trashCans[trashCansIndex] = new TrashCan(xTrashCan, yTrashCan, 0);
				trashCansIndex++;
				lefttrashCans--;
			}
		}
		
		//Generate trash cans on the right
		int righttrashCans = (int) Math.floor(trashCans/2.0);  //sรณ explicitando o floor e o ceil
		x = 8*field.length/12;
		y = 2*field.length/12;
		while(righttrashCans > 0) {
			int xOffset = rand.nextInt(thrashcanAreaWidth);
			int yOffset = rand.nextInt(thrashcanAreaHeight);
			
			xTrashCan = x + xOffset;
			yTrashCan = y + yOffset;
			
			if (field[yTrashCan][xTrashCan] == Tile.EMPTY) {
				field[yTrashCan][xTrashCan] = Tile.TRASHCAN;
				this.trashCans[trashCansIndex] = new TrashCan(xTrashCan, yTrashCan, 0);
				trashCansIndex++;
				righttrashCans--;
			}
		}
		
		System.out.println("Total trashCans: " + trashCans);
	}

	private void generateJunk() {
		double junkRate = 0.4 + new Random().nextDouble() * 0.45;
		int junk = (int) (junkRate * (field.length * field[0].length));
		
		int emptySpace = field.length * field[0].length - totalWalls - totaltrashCans;
		if (junk > emptySpace) {
			junk = emptySpace;
			junkRate = junk/(1.0 * field.length * field[0].length);
		}
		
		System.out.println("Total junk: " + junk + " (" + (int)(junkRate*100) + "%)");
		
		Random rand = new Random();
		
		while(junk > 0) {
			int x = rand.nextInt(field.length);
			int y = rand.nextInt(field[0].length);
			
			if(field[y][x] == Tile.EMPTY) {
				field[y][x] = Tile.JUNK;
				junk--;
			}
		}
		
	}

	private void buildWalls() {
		totalWalls = 0;

		if(field.length < 5) return;
		
		int wallHeight = (int) Math.floor( ((8.0/12.0) * field.length) );// * field.length;
				
		int wallWidth = (int) Math.floor( field.length / 3.0 / 2.0 );//* field.length;
		int wallX = (int) Math.ceil( field.length / 3.0 / 2.0 );
		
		
		int wallY = (int) Math.ceil( ((2.0/12.0) * field.length) );
		
		buildWall(wallX, wallY, wallWidth, wallHeight);
		buildWall(wallX + field.length/2, wallY, wallWidth, wallHeight);
		System.out.println("Total walls: " + totalWalls);
	}
	
	private void buildWall(int x, int y, int width, int height) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				this.field[j][i] = Tile.WALL;
				totalWalls++;
			}
		}
		makeHoleInWall(x, y, width, height);
	}
	
	private void makeHoleInWall(int x, int y, int wallWidth, int wallHeight) {
		boolean side = x <= field.length/2 ? true : false;
		
		int holeX = side ? x : x + field.length/12;
		int holeY = y + field.length/12;
		int holeWidth = wallWidth - field.length/12;
		int holeHeight = wallHeight - 2 * (field.length/12);
				
		for (int j = 0; j < holeHeight; j++) {
			for(int i = 0; i < holeWidth; i++) {
				this.field[holeY + j][holeX + i] = Tile.EMPTY;
				totalWalls--;
			}
		}	
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[0].length; x++) {
				String tile;
				
				switch(field[y][x]) {
				default:
				case EMPTY: tile = " "; break;
				case WALL: tile = "W"; break;
				case JUNK: tile = "ท"; break;
				case TRASHCAN: tile = "V"; break;
				case BOT: tile = "@"; break;
				}
				
				sb.append(tile + " ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
