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
		int width = matrix[0].length;
		int height = matrix.length;
		return x < width && y < height && x >= 0 && y >= 0;
	}
	
	public static Point findPoint(Point point, Point[] points) {
		for (Point resultPoint: points ) {
			if(resultPoint.equals(point)) return resultPoint;
		}
		return null;
	}
	
	public String toString() {
		return x + "-" + y;
	}
	
}