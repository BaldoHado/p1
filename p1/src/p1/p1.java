package p1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class p1 {
	
	private static char[][] lines;
	private static ArrayList<Coord> coordinates = new ArrayList<Coord>();
	
	public static void main(String[] args) {
		
		Scanner scanner;
		File f = new File("adamMap.txt");
		
		try {
			scanner = new Scanner(f);
			coordinateBased(scanner);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	public static void coordinateBased(Scanner scan) {		
		int rows = scan.nextInt();
		int cols = scan.nextInt();
		int rooms = scan.nextInt();
		lines = new char[rows*rooms][cols];
		int currRow = 0;
		scan.nextLine();
		//take in cols and # of rows
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			
			//use charAt to grab the elements of the map for a given row
			for (int i = 0; i<rows;i++) {
				lines[currRow][i] = line.charAt(i);
				System.out.print(lines[currRow][i]);
			}
			currRow++;
			System.out.println("");
		}

		for (int i = 0; i<lines.length;i++) {
			for (int j = 0; j<lines[0].length;j++) {
				
				Coord t = new Coord(i,j,lines[i][j]);
				coordinates.add(t);
			
			}
		}
		
		for (int i = 0; i < coordinates.size(); i++) {
			if (coordinates.get(i).getVal() == "K".charAt(0)) {
				findCakeStacks(coordinates.get(i).getRow(),coordinates.get(i).getCol());
				System.out.println("Kirby at " +  coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
				
			}
			
		}
	}
	
	public static void findCakeStacks(int row, int col) {
	
		boolean foundCake = false;
		Stack temp = new Stack();
		Stack vals = new Stack();
		int curRow = row;
		int curCol = col;
/*		for (int i =0;i<coordinates.size();i++) {
			System.out.println("" + coordinates.get(i).getVal() + " " + coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
		}*/	
	
		if (lines[curRow+1][curCol] == ".".charAt(0)) {
	
		}
		if (lines[curRow][curCol+1] == ".".charAt(0)) {
	
		}
		if (lines[curRow-1][curCol] == ".".charAt(0)) {
				
		}
		if (lines[curRow][curCol-1] == ".".charAt(0)) {
			
		}
		
	}
	

}
