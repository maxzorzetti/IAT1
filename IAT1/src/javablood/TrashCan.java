package javablood;

import java.util.ArrayList;
import java.util.List;

import javablood.Environment.Tile;

public class TrashCan {
	private Environment environment;
	private Point coordinates;
	private int capacity;
	private int content;
	
	public TrashCan(Point coordinates, int capacity, Environment environment){
		this.coordinates = coordinates;
		this.capacity = capacity;
		this.environment = environment;
	}
	
	public TrashCan(int x, int y, int capacity, Environment environment){
		this.coordinates = new Point(x, y);
		this.capacity = capacity;
		this.environment = environment;
	}
	
	public Point[] getWalkableAdjacentTiles() {
		List<Point> acessableTiles = new ArrayList<Point>();		
				
		for (int y = coordinates.y - 1; y <= coordinates.y + 1; y++) {
			for(int x = coordinates.x - 1; x <= coordinates.x + 1; x++) {
				Point point = new Point(x, y);
				
				if (!(point.isInsideField(environment.field))) continue;	//Check if it's inside the matrix
				if (point.equals(coordinates)) continue;						//Check if it's the tile we're checking
				if (environment.field[y][x] == Tile.WALL) continue;				//Check if it's a wall
				if (environment.field[y][x] == Tile.TRASHCAN) continue;
				
				acessableTiles.add(new Point(x, y));																			
			}
		}
		
		Point[] result = new Point[acessableTiles.size()];
		return acessableTiles.toArray(result);
	}
	
	public Point getCoordinates(){
		return coordinates;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public void cleanTrashCan(){
		content = 0;
	}
	
	public boolean addTrash(){
		if(content < capacity){
			content++;
			return true;
		}
		return false;
	}
	
	public boolean isFull(){
		if(content >= capacity) return true;
		return false;
	}
	
	public String toString(){
		return "Coordinates: (" + coordinates.x + "," + coordinates.y + ")\nCapacity: " + content + "/" + capacity;
	}
}
