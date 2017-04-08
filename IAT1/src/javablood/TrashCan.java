package javablood;

public class TrashCan {
	private Point coordinates;
	private int capacity;
	private int content;
	
	public TrashCan(Point coordinates, int capacity){
		this.coordinates = coordinates;
		this.capacity = capacity;
	}
	
	public TrashCan(int x, int y, int capacity){
		this.coordinates = new Point(x, y);
		this.capacity = capacity;
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
