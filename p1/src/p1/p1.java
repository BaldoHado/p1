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
	private static ArrayDeque<Coord> path;
	private static ArrayList<Coord> optPath;
	private static ArrayList<Coord> doors;
	private static ArrayList<Coord> kirbyLoc; 
	private static Stack<Coord> tempSt;
	private static Stack<Coord> valsSt;
	private static int rows;
	private static int cols;
	private static int rooms;
	private static int tRooms;
	private static int tDoor;
	private static Coord Cake;
	private static int amtDoors;
	private static boolean pathFin; 
	
	public static void main(String[] args) {
		
		Scanner scanner;
		File f = new File("adamMap.txt");
		temp = new ArrayDeque<Coord>();
		vals = new ArrayDeque<Coord>();
		path = new ArrayDeque<Coord>();
		doors = new ArrayList<Coord>();
		kirbyLoc = new ArrayList<Coord>();
		optPath = new ArrayList<Coord>();
		pathFin = false;
		tDoor = 0;
		

		try {
			scanner = new Scanner(f);
			coordinateBased(scanner);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
	}

	
	public static void coordinateBased(Scanner scan) {		
		rows = scan.nextInt();
		cols = scan.nextInt();
		rooms = scan.nextInt();
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
				
				findCakeQueue(c);
				makePath(c,vals.remove());
				System.out.println(Cake.getRow() + " " + Cake.getCol());
				break;
				//System.out.println("Kirby at " +  coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
				
				//System.out.println("T SIZE = " + temp.size());
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
				findCakeQueue(c);
				System.out.println(Cake.getRow());
				makePath(c,vals.remove());
				System.out.println("Kirby at " +  coords.get(i).getRow() + " " +coords.get(i).getCol());
				System.out.println("T SIZE = " + temp.size());
			}
		}
	}
	
	public static void makePath(Coord curr, Coord c) { //if multiple rooms, look for door first, then last floor look for cake
		System.out.println("tRooms: " + tRooms + " rooms: " + rooms);
		if (pathFin == false) { 
			if (tRooms == rooms-1) {
			if (vals.peek().getVal() == "C".charAt(0)) {
				optPath.add(new Coord(vals.peek().getRow(),vals.peek().getCol(),vals.peek().getVal()));
				pathFin = true;
			} else if ((Math.abs(c.getRow()-Cake.getRow()) < Math.abs(curr.getRow()-Cake.getRow())) ||(Math.abs(c.getCol()-Cake.getCol()) < Math.abs(curr.getCol()-Cake.getCol()))) {
				path.add(c);
				optPath.add(c);
				System.out.println(" WALKING TO KIRBS " + c.getRow() + " " + c.getCol());
				if (vals.size() >= 1) {
					//System.out.println(vals.remove().getRow());
					makePath(c,vals.remove());
					//System.out.println("Vals: " + vals.size());
				}
				
			} else {
				if (vals.size() >= 1) {
					System.out.println("Fail");
					//System.out.println("Vals: " + vals.size());
					makePath(curr,vals.remove());
				}
			}
		} else {
			//System.out.println(" DOOR PEEK " + doors.get(tRooms).getRow() + " " + doors.get(tRooms).getCol() + " " + doors.get(tRooms).getVal());
			//System.out.println(" VAL PEEK " + vals.peek().getRow() + " " + vals.peek().getCol());
			//System.out.println(Integer.valueOf(vals.peek().getRow()) == Integer.valueOf(doors.get(tRooms).getRow()) && Integer.valueOf(vals.peek().getRow()) == Integer.valueOf(doors.get(tRooms).getCol()));
			if (vals.peek().getVal() == "|".charAt(0)) {				
				System.out.println("YO DOOR" + " " + vals.peek().getRow() + " " + vals.peek().getCol() +" "+  vals.peek().getVal());
				//System.out.println("Starting kirby at " + kirbyLoc.get(tRooms).getRow() + " " + kirbyLoc.get(tRooms).getCol());
				//System.out.println(vals.size() + "Kib size " + kirbyLoc.size());
				optPath.add(new Coord(vals.peek().getRow(),vals.peek().getCol(),vals.peek().getVal()));
				vals.remove();
				tRooms++;
				makePath(kirbyLoc.get(tRooms-1),vals.remove());
			}else if ((Math.abs(c.getRow()-doors.get(tRooms).getRow()) < Math.abs(curr.getRow()-doors.get(tRooms).getRow())) || (Math.abs(c.getCol()-doors.get(tRooms).getCol()) < Math.abs(curr.getCol()-doors.get(tRooms).getCol()))  ) {
					path.add(c);
					System.out.println("DOOR:" + c.getRow() + " " + c.getCol());
					if (vals.size() >= 1) {
						//System.out.println(vals.remove().getRow());
						System.out.println("MAKING PATH");
						System.out.println(vals.peek().getRow() + " " + vals.peek().getCol() +" "+  vals.peek().getVal());
						optPath.add(c);
						makePath(c,vals.remove());	
						//System.out.println("Vals: " + vals.size());
					}
			}else {
				if (vals.size() >= 1) {
					System.out.println("Fail");
					//System.out.println("Vals: " + vals.size());
					makePath(curr,vals.remove());
				}
			}
		}
		}
		
		
	}
	
	public static void findCakeQueue(Coord c) {
		
		int curRow = c.getRow();
		int curCol = c.getCol();
		System.out.println(c.getRow() + " " + c.getCol());
		
		if (lines[curRow][curCol] == "C".charAt(0)) {
			System.out.println("C at " + c.getRow() + " " + c.getCol());
			Cake = c;
			vals.add(c);
			System.out.println(vals.peek().getRow());
			while(temp.size()>0) {
				temp.remove();
			}
			System.out.println("VALS SIZE: " + vals.size() + " TEMP SIZE: " + temp.size());

		}
		if(lines[curRow][curCol] == "|".charAt(0)) {
			System.out.println(vals.size() + " SiZE");
			vals.add(new Coord(curRow,curCol,lines[curRow][curCol]));
			doors.add(new Coord(curRow,curCol,lines[curRow][curCol]));
			System.out.println("Door detected" + " DOOR SIZE: " + doors.size() + " door row" + c.getRow() + " " + c.getCol());
			
			int startRow = 0;
			int startCol = 0;
			amtDoors++;
			for (int i = amtDoors*rows-1; i < (amtDoors+1)*rows-1; i++) {
				for (int j = 0; j < cols;j++) {
					if (lines[i][j] == "K".charAt(0)) {
						kirbyLoc.add(new Coord(i,j,lines[i][j]));
						System.out.println("I " + i + " J " + j);
						startRow = i;
						startCol = j;
						break;
					}
				}
				if (startRow != 0) {
					break;
				}
			}
			while (temp.size() > 0) {
				temp.remove();
			}
			Coord d = new Coord(startRow,startCol,lines[startRow][startCol]);
			temp.add(d);
			findCakeQueue(d);
		}
		if (curRow > (amtDoors*rows-1) && curCol > 0 && curRow < ((amtDoors+1)*rows-1) && curCol < lines[0].length-1 && lines[curRow][curCol] != "C".charAt(0) && lines[curRow][curCol] != "|".charAt(0)) {
	
			if ((lines[curRow+1][curCol] == ".".charAt(0) || lines[curRow+1][curCol] == "C".charAt(0) || lines[curRow+1][curCol] == "|".charAt(0))) {
				if (existsAlready(curRow+1,curCol) == false) {
					temp.add(new Coord(curRow+1,curCol,lines[curRow][curCol]));
					//System.out.println("add 1 ");
				}
				
			}
			if ((lines[curRow][curCol+1] == ".".charAt(0) || lines[curRow][curCol+1] == "C".charAt(0) || lines[curRow][curCol+1] == "|".charAt(0)) ) {
				if ( existsAlready(curRow,curCol+1) == false) {
					temp.add(new Coord(curRow,curCol+1,lines[curRow][curCol+1]));
					//System.out.println("add 2 ");
				}
				
			}
			if ((lines[curRow-1][curCol] == ".".charAt(0) || lines[curRow-1][curCol] == "C".charAt(0) || lines[curRow-1][curCol] == "|".charAt(0))  ) { 
				if ( existsAlready(curRow-1,curCol) == false) {
					temp.add(new Coord(curRow-1,curCol,lines[curRow-1][curCol]));
					//System.out.println("add 3 ");
				}
				
			}
			if ((lines[curRow][curCol-1] == ".".charAt(0) || lines[curRow][curCol-1] == "C".charAt(0) || lines[curRow][curCol-1] == "|".charAt(0)) ) {
				if (existsAlready(curRow,curCol-1) == false) {
					temp.add(new Coord(curRow,curCol-1,lines[curRow][curCol-1]));
					//System.out.println("add 4 ");
				}
				
			}
			while (temp.size() > 0) {
				Coord t = temp.remove();
				vals.add(t);
				findCakeQueue(t);
				//System.out.println(temp.size());
				//System.out.println(" " + vals.size());
			}
		}
		
	}
	/*public static void findCakeStacks(Coord c) {
		
		int curRow = c.getRow();
		int curCol = c.getCol();
		System.out.println(c.getRow() + " " + c.getCol());
		if (lines[curRow][curCol] == "C".charAt(0)) {
			System.out.println("C at " + c.getRow() + " " + c.getCol());
			Cake = c;
			
		}
		if(lines[curRow][curCol] == "|".charAt(0)) {
			doorCoord = new Coord(curRow,curCol,lines[curRow][curCol]);
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
				findCakeStacks(t);
				//System.out.println(temp.size());
				//System.out.println(" " + vals.size());
			}
		}
		
	}*/
	
	public static Coord sideBySide(Coord c) {
		int tRow = c.getRow();
		int tCol = c.getCol();
		ArrayDeque<Coord> tList = new ArrayDeque<Coord>();
		Coord low = null;
		boolean found = false;
		
		while (vals.size() > 0 && found == false) {
			if ((Math.abs(vals.peek().getRow() - tRow) == 1 && vals.peek().getCol() - tCol == 0) || Math.abs(vals.peek().getCol()-tCol) == 1 && vals.peek().getRow()- tRow == 0) {
				low = new Coord(vals.peek().getRow(),vals.peek().getCol(),vals.peek().getVal());
				
			} else {
				Coord t = vals.remove();
				tList.add(t);
			}
		}
		
		while (tList.size() > 0) {
			Coord t = tList.remove();
			vals.add(t);
		}
		
		return low;
		
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


