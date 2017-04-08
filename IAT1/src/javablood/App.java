package javablood;

public class App {

	public static void main(String args[]) {
		
		Environment env = new Environment(12, 5);
		
		System.out.println(env);
		
		Trashbot doty = new Trashbot(env, new AStar(), 5);
		
		doty.cleanEnvironment();
	}
}
