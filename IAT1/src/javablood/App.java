package javablood;

import java.io.IOException;

public class App {

	public static void main(String args[]) throws IOException {
		int fieldSize = 12;
		if(args.length > 0) fieldSize = Integer.parseInt(args[0]);
		
		int trashCanCount = fieldSize/2 - 1;
		if(args.length > 1) trashCanCount = Integer.parseInt(args[1]);		

		int trashbotCapacity = fieldSize / 2;
		if(args.length > 2) trashbotCapacity = Integer.parseInt(args[2]);		
	
		boolean pushToPrintEnabled = true;
		if(args.length > 3) pushToPrintEnabled = false;
		
		Environment env = new Environment(fieldSize, trashCanCount);
		
		System.out.println(env);
		
		double time = System.currentTimeMillis();
		
		Trashbot doty = new Trashbot(env, new AStar(), trashbotCapacity);
		//doty.enablePrint = false;
		doty.pushToPrint = pushToPrintEnabled;
		doty.cleanEnvironment();
		
		System.out.println(System.currentTimeMillis() - time);
	}
}
