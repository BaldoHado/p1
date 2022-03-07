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
	private static ArrayDeque<Coord> temp;
	private static ArrayDeque<Coord> vals;
	
	public static void main(String[] args) {
		
		Scanner scanner;
		File f = new File("adamMap.txt");
		temp = new ArrayDeque<Coord>();
		vals = new ArrayDeque<Coord>();

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
				Coord c = new Coord(coordinates.get(i).getRow(),coordinates.get(i).getCol(),coordinates.get(i).getVal());
				findCakeStacks(c);
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
				Coord c = new Coord(coords.get(i).getRow(),coords.get(i).getCol(),coords.get(i).getVal());
				findCakeStacks(c);
				System.out.println("Kirby at " +  coords.get(i).getRow() + " " +coords.get(i).getCol());
				
			}
			
		}
	}
	public static void findCakeStacks(Coord c) {
	
		int curRow = c.getRow();
		int curCol = c.getCol();
		System.out.println(c.getRow() + " " + c.getCol());
		if (lines[curRow][curCol] == "C".charAt(0)) {
			System.out.println("C at " + c.getRow() + " " + c.getCol());
		}
		if (curRow > 0 && curCol > 0 && curRow < lines.length-1 && curCol < lines[0].length-1) {
			
			if ((lines[curRow+1][curCol] == ".".charAt(0) || lines[curRow+1][curCol] == "C".charAt(0))) {
				if (existsAlready(curRow+1,curCol) == false) {
					temp.add(new Coord(curRow,curCol,lines[curRow][curCol]));
					//System.out.println("add 1 ");
				}
				
			}
			if ((lines[curRow][curCol+1] == ".".charAt(0) || lines[curRow][curCol+1] == "C".charAt(0)) ) {
				if ( existsAlready(curRow,curCol+1) == false) {
					temp.add(new Coord(curRow,curCol+1,lines[curRow][curCol+1]));
					//System.out.println("add 2 ");
				}
				
			}
			if ((lines[curRow-1][curCol] == ".".charAt(0) || lines[curRow-1][curCol] == "C".charAt(0)) ) { 
				if ( existsAlready(curRow-1,curCol) == false) {
					temp.add(new Coord(curRow-1,curCol,lines[curRow-1][curCol]));
					//System.out.println("add 3 ");
				}
				
			}
			if ((lines[curRow][curCol-1] == ".".charAt(0) || lines[curRow][curCol-1] == "C".charAt(0)) ) {
				if (existsAlready(curRow,curCol-1) == false) {
					temp.add(new Coord(curRow,curCol-1,lines[curRow][curCol-1]));
					//System.out.println("add 4 ");
				}
				
			}
			while (temp.size() > 0) {
				Coord t = temp.remove();
				vals.add(t);
				//System.out.println(temp.size());
				//System.out.println(" " + vals.size());
				findCakeStacks(t);
				
			}
		}
		
		
	}
	
	public static boolean existsAlready(int row, int col) {
		ArrayDeque<Coord> list = new ArrayDeque<Coord>();
		ArrayDeque<Coord> list2 = new ArrayDeque<Coord>();
		boolean found = false;
		//System.out.println(temp.size());
		while (temp.size() > 0 && found == false) {
			//System.out.println("running");
			if (temp.peek().getRow() == row && temp.peek().getCol() == col && temp.peek().getVal() == lines[row][col]) {
				found = true;
			}
			Coord t = temp.remove();
			list.add(t);
			//System.out.println("list" + list.size());
		}
		while(vals.size() > 0 && found == false) {
			if (vals.peek().getRow() == row && vals.peek().getCol() == col && vals.peek().getVal() == lines[row][col]) {
				found = true;
			}
			Coord t = vals.remove();
			list2.add(t);
		}
		while (list.size() > 0) {
			Coord c = list.remove();
			//System.out.println("running2");
			temp.add(c);
		}
		while (list2.size() > 0) {
			Coord c = list2.remove();
			//System.out.println("running2");
			vals.add(c);
		}
	
		//System.out.println(found);
		
		
		return found;
		
		
	}

}


