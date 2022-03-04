package p1;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class p1 {
	
	private static char[][] lines;
	private static ArrayList<Coord> coordinates = new ArrayList<Coord>();
	private static ArrayList<Coord> coords = new ArrayList<Coord>();
	
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
				//findCakeStacks(coordinates.get(i).getRow(),coordinates.get(i).getCol());
				System.out.println("Kirby at " +  coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
				
			}
			
		}
	}
	
	public static void coordinateBasedArr(Scanner scan)  {
		scan.nextLine();
		//take in cols and # of rows
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
	
		
			Coord t = new Coord(Integer.valueOf(String.valueOf(line.charAt(1))),Integer.valueOf(String.valueOf(line.charAt(2))),line.charAt(0));
			//System.out.println(t.getRow());
			coords.add(t);
			
		}
			
		for (int i = 0; i < coords.size(); i++) {
			if (coords.get(i).getVal() == "K".charAt(0)) {
				//findCakeStacks(coordinates.get(i).getRow(),coordinates.get(i).getCol());
				System.out.println("Kirby at " +  coords.get(i).getRow() + " " +coords.get(i).getCol());
				
			}
			
		}
	}
	public static void findCakeStacks(int row, int col) {
	
		ArrayDeque<Coord> temp = new ArrayDeque<Coord>();
		ArrayDeque<Coord> vals = new ArrayDeque<Coord>();
		int curRow = row;
		int curCol = col;
		for (int i =0;i<coordinates.size();i++) {
			System.out.println("" + coordinates.get(i).getVal() + " " + coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
		}
		if (curRow > 0 && curCol > 0) {
			if (lines[curRow+1][curCol] == ".".charAt(0)) {
				temp.add(new Coord(curRow,curCol,lines[curRow][curCol]));
			}
			if (lines[curRow][curCol+1] == ".".charAt(0)) {
				temp.add(new Coord(curRow,curCol+1,lines[curRow][curCol+1]));
			}
			if (lines[curRow-1][curCol] == ".".charAt(0)) {
				temp.add(new Coord(curRow-1,curCol,lines[curRow-1][curCol]));
			}
			if (lines[curRow][curCol-1] == ".".charAt(0)) {
				temp.add(new Coord(curRow,curCol-1,lines[curRow][curCol-1]));
			}
		}
		
		
	}

}


