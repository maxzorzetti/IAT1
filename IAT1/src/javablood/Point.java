package javablood;

public class Point { 
	int x, y; 
	
	Point (int x, int y) { 
		this.x = x; 
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof Point)) return false;
		
		Point point = (Point) obj;
		
		return this.x == point.x && this.y == point.y;	
	}
	
	@Override
	public int hashCode(){
		String hash = x + " " + y;
		return hash.hashCode();
	}
	
	public boolean isInsideField(Object[][] matrix){
		return this.x < matrix[0].length && this.x >= 0 && this.y < matrix.length && this.y < matrix[0].length;
	}
	
	public static Point findPoint(Point point, Point[] points) {
		for (Point resultPoint: points ) {
			if(resultPoint.equals(point)) return resultPoint;
		}
		return null;
	}
	
	public String toString() {
		return x + " " + y;
	}
	
}