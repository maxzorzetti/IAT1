package javablood;

import java.io.IOException;

public class App {

	public static void main(String args[]) throws IOException {
		
		Environment env = new Environment(12, 5);
		
		System.out.println(env);
		
		Trashbot doty = new Trashbot(env, new AStar(), 10);
		doty.pushToPrint = true;
		doty.cleanEnvironment();

	}
}
