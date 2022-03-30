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
	private static ArrayList<Coord> reTest;
	private static ArrayList<Coord> doors;
	private static ArrayList<Coord> kirbyLoc;  
	private static Stack<Coord> tempSt;
	private static Stack<Coord> valsSt;
	private static double runtime;
	private static boolean outcoord;
	private static boolean foundIt;
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
		outcoord = false;
			while (true) {
			Scanner sa = new Scanner(System.in);
			String li = sa.nextLine();
			if (li.length() > 0) {
			File f = new File("adamMap.txt");
			if (li.indexOf("--Incoordinate") >= 0) {
				f = new File("adamCoordinateMap");
			}
			if (li.indexOf("--Outcoordinate") >= 0) {
				outcoord = true;
			}
			if (li.indexOf("--Help ") >= 0 || li.indexOf("--Help") >= 0) {
				System.out.println("--Stack : Runs path program with Stacks");
				System.out.println("--Queue : Runs path program with Queues");
				System.out.println("--Opt : Runs optimal path");
				System.out.println("--Time : Returns runtime");
				System.out.println("--Incoordinate : Uses a map (no points)");
				System.out.println("--Outcoordinate : Uses a map of points (coordinates)");
				
			}
			temp = new ArrayDeque<Coord>();
			vals = new ArrayDeque<Coord>();
			path = new ArrayDeque<Coord>();
			doors = new ArrayList<Coord>();
			kirbyLoc = new ArrayList<Coord>();
			optPath = new ArrayList<Coord>();
			reTest = new ArrayList<Coord>();
			tempSt = new Stack<Coord>();
			valsSt = new Stack<Coord>();
			tDoor = 0;
			
			try {
				
				scanner = new Scanner(f);
				if (li.indexOf("--Stack") >= 0) {
					coordinateBasedStacks(scanner);
				}
				if (li.indexOf("--Queue") >= 0) {
					coordinateBased(scanner);
				}
				if (li.indexOf("--Opt") >= 0) {
					coordinateBased(scanner);
				}
				if (li.indexOf("--Time") >= 0) {
					System.out.println(runtime);
				}
				
				
				
			}catch(Exception e) {
				System.out.println(e);
			}
			}
     
		
			}
	}
	

	public static void coordinateBased(Scanner scan) {	
		long start = System.nanoTime();
		rows = scan.nextInt();
		cols = scan.nextInt();
		rooms = scan.nextInt();
		lines = new char[rows*rooms][cols];
		
		int currRow = 0;
		scan.nextLine();
		String l  = scan.nextLine();
		int counter = 0;
		if (l.indexOf("0") > 0) { // checks if theres a zero in the second line, which will indicate that it is a coordinate map
		
			int count = 0;
			while (scan.hasNextLine()) {
				lines[Integer.valueOf(l.substring(1,2))][Integer.valueOf(l.substring(2,3))] = l.charAt(0);
				//System.out.println(count + " " + l.substring(1,2) + " " + l.substring(2,3));
				l = scan.nextLine();
				count++;
			}
			// last iteration because it won't register having a next line
			lines[Integer.valueOf(l.substring(1,2))][Integer.valueOf(l.substring(2,3))] = l.charAt(0);
			//System.out.println(count + " " + l.substring(1,2) + " " + l.substring(2,3)); 
			
			for (int i = 0; i < lines.length;i++) {
				for (int j = 0 ; j<lines[0].length;j++) {
					System.out.print(lines[i][j]);
				}
				System.out.println();
			}
			
		} else {
		//take in cols and # of rows
		while(scan.hasNextLine()) {
			if (counter > 0) {
				l = scan.nextLine();
			}
			//use charAt to grab the elements of the map for a given row
			for (int i = 0; i<rows;i++) {
				lines[currRow][i] = l.charAt(i);
				//System.out.print(lines[currRow][i]);
			}
			counter++;
			currRow++;
			//System.out.println("");
		}
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
				kirbyLoc.add(c);
				findCakeQueue(c);
				//removeDups();
				removeKirby();
				for (Coord obj: vals) {
					System.out.println(obj.getRow() + " " +obj.getCol() + " " + obj.getVal());
					
				}
				makePath(c,vals.remove());
				System.out.println("OPT PATH " + optPath.size());
				for (int k = 0 ; k < optPath.size(); k++) {
					if (outcoord == true)
					System.out.println(optPath.get(k).getVal() + " " + optPath.get(k).getRow() + " " + optPath.get(k).getCol());
					
					if(lines[optPath.get(k).getRow()][optPath.get(k).getCol()] != "|".charAt(0) &&  lines[optPath.get(k).getRow()][optPath.get(k).getCol()] != "C".charAt(0)) {
						lines[optPath.get(k).getRow()][optPath.get(k).getCol()] = "+".charAt(0);
					}
				}
				if (outcoord == false) {
				for (int z = 0; z< lines.length;z++) {
					for (int j = 0 ; j<lines[0].length;j++) {
						System.out.print(lines[z][j]);
					}
					System.out.println();
				}
				}
				//System.out.println(Cake.getRow() + " " + Cake.getCol());
				break;
				//System.out.println("Kirby at " +  coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
				
				//System.out.println("T SIZE = " + temp.size());
			}
			
		
		}
		long end = System.nanoTime();
		runtime = (end-start)/1000000.0;
		
		
	}
	
	public static void coordinateBasedStacks(Scanner scan)  {
		long start = System.nanoTime();
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
				//System.out.print(lines[currRow][i]);
			}
			currRow++;
			//System.out.println("");
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
				removeDupStack();
				removeKirby();
				for (Coord obj : kirbyLoc) {
					//System.out.println("K at " + obj.getRow() + " " + obj.getCol());
				}
				//makePath(c,valsSt.pop());
				
				makePathStacks(c,valsSt.pop());
				for (int k = 0 ; k < optPath.size(); k++) {
					if (outcoord == true)
					System.out.println(optPath.get(k).getVal() + " " + optPath.get(k).getRow() + " " + optPath.get(k).getCol());
					
					if(lines[optPath.get(k).getRow()][optPath.get(k).getCol()] != "|".charAt(0) &&  lines[optPath.get(k).getRow()][optPath.get(k).getCol()] != "C".charAt(0)) {
						lines[optPath.get(k).getRow()][optPath.get(k).getCol()] = "+".charAt(0);
					}
				}
				if (outcoord == false) {
				for (int z = 0; z< lines.length;z++) {
					for (int j = 0 ; j<lines[0].length;j++) {
						System.out.print(lines[z][j]);
					}
					System.out.println();
				}
				}
				
//				for (Coord obj : valsSt) {
//					System.out.println(obj.getRow() + " " + obj.getCol() + " " + obj.getVal());
//				}
				System.out.println(Cake.getRow() + " " + Cake.getCol());
				break;
				//System.out.println("Kirby at " +  coordinates.get(i).getRow() + " " +coordinates.get(i).getCol());
				
				//System.out.println("T SIZE = " + temp.size());
			}
			
		}
		long end = System.nanoTime();
		runtime = (start-end)/1000000.0;
	}
	
	public static void makePath(Coord curr, Coord c) { //if multiple rooms, look for door first, then last floor look for cake
		//System.out.println("tRooms: " + tRooms + " rooms: " + rooms);
		//System.out.println(vals.size());
		System.out.println("Curr: " + curr.getRow()+ " " + curr.getCol() + " " + curr.getVal());
		System.out.println("C : " + c.getRow()  + " " + c.getCol());
		if (curr.getVal() == "|".charAt(0)) {
			pathFin = true;
		}
		//System.out.println("Peeking : " + vals.peek().getRow() + " " + vals.peek().getCol() + " " + vals.peek().getVal());
		if (pathFin == false) { 
			if (tRooms == rooms-1) {
				//System.out.println("CAKE : ");
				//System.out.println(" IS " + Cake.getRow());
			if (c.getRow() == Cake.getRow() && c.getCol() == Cake.getCol() && c.getVal() == "C".charAt(0) && sideBySide(curr,c)) {
				optPath.add(c);

				pathFin = true;
			} else if (((Math.abs(c.getRow()-Cake.getRow()) < Math.abs(curr.getRow()-Cake.getRow())) ||(Math.abs(c.getCol()-Cake.getCol()) < Math.abs(curr.getCol()-Cake.getCol()))) && sideBySide(curr, c) ) {
				path.add(c);
				optPath.add(c);
				System.out.println(" WALKING TO KIRBS " + c.getRow() + " " + c.getCol());
				if (vals.size() >= 2) {
					//System.out.println(vals.remove().getRow());
					makePath(c,vals.remove());
					//System.out.println("Vals: " + vals.size());
				}
				
			} else {
				if (vals.size() >= 1) {
					//System.out.println("Fail");
					//System.out.println("Vals: " + vals.size());
					vals.add(c);
					makePath(curr,vals.remove());
				}
			}
		} else {
			//System.out.println(" DOOR PEEK " + doors.get(tRooms).getRow() + " " + doors.get(tRooms).getCol() + " " + doors.get(tRooms).getVal());
			//System.out.println(" VAL PEEK " + vals.peek().getRow() + " " + vals.peek().getCol());
			//System.out.println(Integer.valueOf(vals.peek().getRow()) == Integer.valueOf(doors.get(tRooms).getRow()) && Integer.valueOf(vals.peek().getRow()) == Integer.valueOf(doors.get(tRooms).getCol()));
			if (c.getVal() == "|".charAt(0) && sideBySide(curr,c)) {				
				//System.out.println("YO DOOR" + " " + vals.peek().getRow() + " " + vals.peek().getCol() +" "+  vals.peek().getVal());
				//System.out.println("Starting kirby at " + kirbyLoc.get(tRooms).getRow() + " " + kirbyLoc.get(tRooms).getCol());
				//System.out.println(vals.size() + "Kib size " + kirbyLoc.size());
				optPath.add(c);
				tRooms++;
				makePath(kirbyLoc.get(tRooms),vals.remove());
			}else if (((Math.abs(c.getRow()-doors.get(tRooms).getRow()) < Math.abs(curr.getRow()-doors.get(tRooms).getRow()) && sideBySide(curr,c) == true) || (Math.abs(c.getCol()-doors.get(tRooms).getCol() ) < Math.abs(curr.getCol()-doors.get(tRooms).getCol()) && sideBySide(curr,c) == true)) &&  c.getVal() != "!".charAt(0)  ) {
					path.add(c);
					System.out.println("DOOR:" + c.getRow() + " " + c.getCol());
					if (vals.size() >= 1) {
						//System.out.println(vals.remove().getRow());
					//	System.out.println("MAKING PATH");
						//System.out.println(vals.peek().getRow() + " " + vals.peek().getCol() +" "+  vals.peek().getVal());
						optPath.add(c);
						makePath(c,vals.remove());	
						//System.out.println("Vals: " + vals.size());
					}
			}else {
				if (vals.size() >= 1) {
					//System.out.println("Fail");
				//	System.out.println("Vals: " + vals.size());
					vals.add(c);
					makePath(curr,vals.remove());
				}
			}
		}
		}
		
		
	}
	public static void makePathStacks(Coord curr, Coord c) { //if multiple rooms, look for door first, then last floor look for cake
		//System.out.println("tRooms: " + tRooms + " rooms: " + rooms);
		removeDup();
		//System.out.println("vals" + valsSt.size());
		//System.out.println("Curr: " + curr.getRow()+ " " + curr.getCol() + " " + curr.getVal() );
		//System.out.println("C : " + c.getRow()  + " " + c.getCol());
		//System.out.println("Peeking : " + valsSt.peek().getRow() + " " + valsSt.peek().getCol() + " " + valsSt.peek().getVal());
		if (c.getRow() == -1) {
			//System.out.println("Negative");
			//System.out.println("Vals: " + valsSt.size());
			makePathStacks(curr,valsSt.pop());
		} else {
		if (pathFin == false) { 
			if (tRooms == rooms-1) {
			if (c.getRow() == Cake.getRow() && c.getCol() == Cake.getCol() && c.getVal() == "C".charAt(0)) {
				optPath.add(c);
				//System.out.println("FOUND CAK");
				pathFin = true;
			} else if ((Math.abs(c.getRow()-Cake.getRow()) < Math.abs(curr.getRow()-Cake.getRow())) ||(Math.abs(c.getCol()-Cake.getCol()) < Math.abs(curr.getCol()-Cake.getCol())) && sideBySide(curr, c) ) {
				path.add(c);
				optPath.add(c);
				//System.out.println(" WALKING TO KIRBS " + c.getRow() + " " + c.getCol());
				if (valsSt.size() >= 2) {
					//System.out.println(vals.remove().getRow());
					makePathStacks(c,valsSt.pop());
					//System.out.println("Vals: " + vals.size());
				}
				
			} else {
				if (valsSt.size() >= 1) {
					//System.out.println("Fail");
					//System.out.println("Vals: " + valsSt.size());
					valsSt.add(0,c);
					makePathStacks(curr,valsSt.pop());
				}
			}
		} else {
			//System.out.println(" DOOR PEEK " + doors.get(tRooms).getRow() + " " + doors.get(tRooms).getCol() + " " + doors.get(tRooms).getVal());
			//System.out.println(" VAL PEEK " + vals.peek().getRow() + " " + vals.peek().getCol());
			//System.out.println("Youre on the first floor");
			//System.out.println(Integer.valueOf(vals.peek().getRow()) == Integer.valueOf(doors.get(tRooms).getRow()) && Integer.valueOf(vals.peek().getRow()) == Integer.valueOf(doors.get(tRooms).getCol()));
			if (c.getVal() == "|".charAt(0) && sideBySide(curr,c)) {				
				//System.out.println("YO DOOR" + " " + valsSt.peek().getRow() + " " + valsSt.peek().getCol() +" "+  valsSt.peek().getVal());
				//System.out.println("Starting kirby at " + kirbyLoc.get(tRooms).getRow() + " " + kirbyLoc.get(tRooms).getCol());
				//System.out.println(vals.size() + "Kib size " + kirbyLoc.size());
				optPath.add(new Coord(valsSt.peek().getRow(),valsSt.peek().getCol(),valsSt.peek().getVal()));
				valsSt.pop();
				tRooms++;
				makePathStacks(kirbyLoc.get(tRooms-1),valsSt.pop());
			} else if (curr.getVal() == "|".charAt(0) ) {				
					//System.out.println("YO DOOR" + " " + valsSt.peek().getRow() + " " + valsSt.peek().getCol() +" "+  valsSt.peek().getVal());
					//System.out.println("Starting kirby at " + kirbyLoc.get(tRooms).getRow() + " " + kirbyLoc.get(tRooms).getCol());
					//System.out.println(vals.size() + "Kib size " + kirbyLoc.size());
					optPath.add(new Coord(curr.getRow(),curr.getCol(),curr.getVal()));
					valsSt.pop();
					tRooms++;
					makePathStacks(kirbyLoc.get(tRooms-1),valsSt.pop());
			}
			else if ((Math.abs(c.getRow()-doors.get(tRooms).getRow()) < Math.abs(curr.getRow()-doors.get(tRooms).getRow())) || (Math.abs(c.getCol()-doors.get(tRooms).getCol()) < Math.abs(curr.getCol()-doors.get(tRooms).getCol()) && sideBySide(curr,c) == true)  ) {
					path.add(c);
					//System.out.println("DOOR:" + c.getRow() + " " + c.getCol());
					if (valsSt.size() >= 1) {
						//System.out.println(vals.remove().getRow());
						//System.out.println("MAKING PATH");
						//System.out.println(valsSt.peek().getRow() + " " + valsSt.peek().getCol() +" "+  valsSt.peek().getVal());
						optPath.add(c);
						makePathStacks(c,valsSt.pop());	
						//System.out.println("Vals: " + vals.size());
					}
			}else {
				if (valsSt.size() >= 1) {
					//System.out.println("Fail");
					//System.out.println("Vals: " + valsSt.size());
					valsSt.add(0,c);
					
					makePathStacks(curr,valsSt.pop());
				}
			}
		}
		}
		}
		
		
	}	
	public static void findCakeQueue(Coord c) {
		
		int curRow = c.getRow();
		int curCol = c.getCol();
		System.out.println(" CHECKING VAL " + curRow + " " + curCol + " DOORS: " + amtDoors);
		
		if (lines[curRow][curCol] == "C".charAt(0) || c.getVal() == "C".charAt(0)) {
			System.out.println("C at " + c.getRow() + " " + c.getCol() + " " + c.getVal());
			Coord l = new Coord(curRow,curCol,lines[curRow][curCol]);
			Cake = l;
			vals.add(l);
			//System.out.println(vals.peek().getRow());
			System.out.println("CAKE ADDED");
			foundIt= true;
			while(temp.size()>0) {
				temp.remove();
			}
			System.out.println("VALS SIZE: " + vals.size() + " TEMP SIZE: " + temp.size());

		}
		if (foundIt != true) {
			if((lines[curRow][curCol] == "|".charAt(0) || c.getVal() == "|".charAt(0)) && doorExist(curRow,curCol) == false) {
				//System.out.println(vals.size() + " SiZE");
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
				
					Coord t = temp.remove();
					vals.add(t);
					findCakeQueue(t);
	
				}
				Coord d = new Coord(startRow,startCol,lines[startRow][startCol]);
				temp.add(d);
				findCakeQueue(d);
			}
			if (curRow > (amtDoors*rows-1) && curCol > 0 && curRow < (((amtDoors+1)*rows)) && curRow > 0 && curCol < lines[0].length-1 && lines[curRow][curCol] != "C".charAt(0) && lines[curRow][curCol] != "|".charAt(0)) {
				boolean bad = false;
				if ((curRow + 1) >= ((amtDoors + 1) * rows)) {
					bad = true;
				}
				System.out.println(bad);
				if ((bad == false)) {
					if ((lines[curRow + 1][curCol] != "@".charAt(0) && (lines[curRow + 1][curCol] != "K".charAt(0)))) {

						if (existsAlready(curRow + 1, curCol) == false) {
							temp.add(new Coord(curRow + 1, curCol, lines[curRow+1][curCol]));
							System.out.println("add 1 ");
						}

					}
				}

				if ((lines[curRow][curCol+1] != "@".charAt(0) && (lines[curRow][curCol+1] != "K".charAt(0)) )) {
					if ( existsAlready(curRow,curCol+1) == false) {
						temp.add(new Coord(curRow,curCol+1,lines[curRow][curCol+1]));
						System.out.println("add 2 ");
					}
					
				}
				if ((lines[curRow-1][curCol] != "@".charAt(0) && (lines[curRow-1][curCol] != "K".charAt(0))) ) { 
					if ( existsAlready(curRow-1,curCol) == false) {
						temp.add(new Coord(curRow-1,curCol,lines[curRow-1][curCol]));
						System.out.println("add 3 ");
					}
					
				}
				if ((lines[curRow][curCol-1] != "@".charAt(0)) && (lines[curRow][curCol-1] != "K".charAt(0))) {
					if (existsAlready(curRow,curCol-1) == false) {
						temp.add(new Coord(curRow,curCol-1,lines[curRow][curCol-1]));
						System.out.println("add 4 ");
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
		
		
	}
	public static void findCakeStacks(Coord c) {
		int curRow = c.getRow();
		int curCol = c.getCol();
		//System.out.println("Testing " + c.getRow() + " " + c.getCol());
	
		
		if (lines[curRow][curCol] == "C".charAt(0)) {
		//	System.out.println("C at " + c.getRow() + " " + c.getCol() + " " + c.getVal());
			Coord l = new Coord(curRow,curCol,lines[curRow][curCol]);
			Cake = l;
			valsSt.push(l);
			while(tempSt.size()>0) {
				Coord t = tempSt.pop();
				valsSt.push(t);
				findCakeStacks(t);
			}
		//	System.out.println("VALS SIZE: " + valsSt.size() + " TEMP SIZE: " + tempSt.size());

		}
		if(lines[curRow][curCol] == "|".charAt(0)) {
		//	System.out.println(valsSt.size() + " SiZE");
			valsSt.push(new Coord(curRow,curCol,lines[curRow][curCol]));
			doors.add(new Coord(curRow,curCol,lines[curRow][curCol]));
		//	System.out.println("Door detected" + " DOOR SIZE: " + doors.size() + " door row" + c.getRow() + " " + c.getCol());
			
			int startRow = 0;
			int startCol = 0;
			amtDoors++;
			for (int i = amtDoors*rows-1; i < (amtDoors+1)*rows-1; i++) {
				for (int j = 0; j < cols;j++) {
					if (lines[i][j] == "K".charAt(0)) {

						kirbyLoc.add(new Coord(i,j,lines[i][j]));
		//				System.out.println("I " + i + " J " + j);
						startRow = i;
						startCol = j;
						break;
					}
				}
				if (startRow != 0) {
					break;
				}
			}
			while (tempSt.size() > 0) {
				Coord t = tempSt.pop();
				valsSt.push(t);
				findCakeStacks(t);
			}
			Coord d = new Coord(startRow,startCol,lines[startRow][startCol]);
			tempSt.push(d);
		//	System.out.println("Door Passed. Should test " + startRow + " " + startCol);
			findCakeStacks(d);
		}
		if (curRow > (amtDoors*rows-1) && curRow > 0 && curCol > 0 && curRow < ((amtDoors+1)*rows-1) && curCol < lines[0].length-1 && lines[curRow][curCol] != "C".charAt(0) && lines[curRow][curCol] != "|".charAt(0)) {
	
			if ((lines[curRow+1][curCol] == ".".charAt(0) || lines[curRow+1][curCol] == "C".charAt(0) || lines[curRow+1][curCol] == "|".charAt(0))) {
				if (existsAlreadyStack(curRow+1,curCol) == false) {
					tempSt.push(new Coord(curRow+1,curCol,lines[curRow][curCol]));
		//			System.out.println("add 1 ");
				}
				
			}
			if ((lines[curRow][curCol+1] == ".".charAt(0) || lines[curRow][curCol+1] == "C".charAt(0) || lines[curRow][curCol+1] == "|".charAt(0)) ) {
				if (existsAlreadyStack(curRow,curCol+1) == false) {
					tempSt.push(new Coord(curRow,curCol+1,lines[curRow][curCol+1]));
		//			System.out.println("add " + curRow + " " + (curCol+1));
				}
				
			}
			if ((lines[curRow-1][curCol] == ".".charAt(0) || lines[curRow-1][curCol] == "C".charAt(0) || lines[curRow-1][curCol] == "|".charAt(0))  ) { 
				if (existsAlreadyStack(curRow-1,curCol) == false) {
					tempSt.push(new Coord(curRow-1,curCol,lines[curRow-1][curCol]));
		//			System.out.println("add 3 ");
				}
				
			}
			if ((lines[curRow][curCol-1] == ".".charAt(0) || lines[curRow][curCol-1] == "C".charAt(0) || lines[curRow][curCol-1] == "|".charAt(0)) ) {
				if (existsAlreadyStack(curRow,curCol-1) == false) {
					tempSt.push(new Coord(curRow,curCol-1,lines[curRow][curCol-1]));
		//			System.out.println("add 4 ");
				}
				
			}
//			for (Coord obj : tempSt) {
//				System.out.println("TEMPST " + obj.getRow() + " " +obj.getCol() + " " + obj.getVal());
//			}
			while (tempSt.size() > 0) {
				Coord t = tempSt.pop();
				valsSt.push(t);
				
				findCakeStacks(t);
				//System.out.println(temp.size());
				//System.out.println(" " + vals.size());
			}
		//	System.out.println(tempSt.size());
		}
		
		
	}
	
	public static boolean sideBySide(Coord c, Coord d) {
		if (((Math.abs(c.getRow() - d.getRow()) == 1 && (c.getCol() - d.getCol()) == 0)) || ((Math.abs(c.getCol() - d.getCol()) == 1 && (c.getRow() - d.getRow()) == 0)) ) {
			return true;
		}
		return false;
		
	}
	
	public static boolean doorExist(int row, int col) {
		for (Coord obj : doors) {
			if (obj.getRow() == row && obj.getCol() == col) {
				return true;
			}
		} 
		return false;
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
	
	public static boolean existsAlreadyStack(int row, int col) {
		boolean found = false;
		for(Coord obj : tempSt)
		{
		    if (obj.getRow() == row && obj.getCol() == col) {
		    	found = true;
		    }
		}
		for(Coord obj : valsSt)
		{
			if (obj.getRow() == row && obj.getCol() == col) {
		    	found = true;
		    }
		}
		return found;
		
		
	}
	
	public static void removeDupStack() {
		 for (Coord obj : valsSt) {
			 for (Coord obj1 : valsSt) {
				 if (obj.getRow() == obj1.getRow() && obj.getCol() == obj1.getCol() && obj1 != obj) {
					 if ((obj.getVal() == "|".charAt(0) || obj.getVal() == "C".charAt(0)) && obj1.getVal() == ".".charAt(0)) {
						// System.out.println("THIS ONE IS A DUPLICATE " + obj1.getRow() + " " +obj1.getCol() + " TO THIS ONE " + obj.getRow() + " " + obj.getCol());
						 obj1.setRow(-1);
						 obj1.setCol(-1);
						 obj.setVal(".".charAt(0));
					 }
					 if ((obj1.getVal() == "|".charAt(0) || obj1.getVal() == "C".charAt(0)) && obj.getVal() == ".".charAt(0)) {
						// System.out.println("THIS ONE IS A DUPLICATE " + obj.getRow() + " " +obj.getCol()+ " TO THIS ONE " + obj1.getRow() + " " + obj1.getCol());
						 obj.setRow(-1);
						obj.setCol(-1);	 
						obj.setVal(".".charAt(0));
					 }
					 if (obj1.getVal() == "C".charAt(0) && obj.getVal() == "C".charAt(0)) {
						 obj.setRow(-1);
							obj.setCol(-1);	 
							obj.setVal(".".charAt(0));
					 }
					 if (obj1.getVal() == ".".charAt(0) && obj.getVal() == ".".charAt(0)) {
						 obj.setRow(-1);
							obj.setCol(-1);	 
							obj.setVal(".".charAt(0));
					 }
					 if (obj1.getVal() == "|".charAt(0) && obj.getVal() == "|".charAt(0)) {
						 obj.setRow(-1);
							obj.setCol(-1);
							obj.setVal(".".charAt(0));
					 }
					 
					 if (obj1.getVal() == obj.getVal() && obj1 != obj) {
						 obj.setRow(-1);
							obj.setCol(-1);
							obj.setVal(".".charAt(0));
					 }
					 
				 }
			 }
		 }

		 
	}
	public static void removeDups() {
		 for (Coord obj : vals) {
			 for (Coord obj1 : vals) {
				 if (obj.getRow() == obj1.getRow() && obj.getCol() == obj1.getCol() && obj1 != obj) {
					 if ((obj.getVal() == "|".charAt(0) || obj1.getVal() == "C".charAt(0)) && obj1.getVal() == ".".charAt(0)) {
						// System.out.println("THIS ONE IS A DUPLICATE " + obj1.getRow() + " " +obj1.getCol() + " TO THIS ONE " + obj.getRow() + " " + obj.getCol());
						 obj1.setRow(-1);
						 obj1.setCol(-1);
						 obj.setVal(".".charAt(0));
					 }
					 if ((obj1.getVal() == "|".charAt(0) || obj.getVal() == "C".charAt(0)) && obj.getVal() == ".".charAt(0)) {
						// System.out.println("THIS ONE IS A DUPLICATE " + obj.getRow() + " " +obj.getCol()+ " TO THIS ONE " + obj1.getRow() + " " + obj1.getCol());
						 obj.setRow(-1);
						obj.setCol(-1);	 
						obj.setVal(".".charAt(0));
					 }
					 if (obj1.getVal() == "C".charAt(0) && obj.getVal() == "C".charAt(0)) {
						 obj.setRow(-1);
							obj.setCol(-1);	 
							obj.setVal(".".charAt(0));
					 }
					 if (obj1.getVal() == ".".charAt(0) && obj.getVal() == ".".charAt(0)) {
						 obj.setRow(-1);
							obj.setCol(-1);	 
							obj.setVal(".".charAt(0));
					 }
//					 if (obj1.getVal() == "|".charAt(0) && obj.getVal() == "|".charAt(0)) {
//						 obj.setRow(-1);
//							obj.setCol(-1);
//							obj.setVal(".".charAt(0));
//					 }
				 }
			 }
		 }

		 
	}
	
	

	public static void removeDup() {
		//System.out.println(" DUP : " + valsSt.peek().getRow() + " " + valsSt.peek().getCol());
		Coord t = valsSt.pop();
		//System.out.println(" DUP : " + t.getRow() + " " + t.getCol());
		//System.out.println(" DUP : " + valsSt.peek().getRow() + " " + valsSt.peek().getCol());
		if (valsSt.peek().getRow() == t.getRow() && valsSt.peek().getCol() == t.getCol()) 
			System.out.println("");
		else  {
			//System.out.println("not dup") ;
			valsSt.push(t);
		}
			
	}
	
	public static void removeKirby() {
		boolean k = false;
		for (Coord obj : vals) {
			if (obj.getVal() == "K".charAt(0)) {
				//System.out.println("KIRB " + obj.getRow() + " " + obj.getCol());
				for (Coord item : kirbyLoc) {
					//System.out.println("KIRBS " + item.getRow() + " " + item.getCol());
					if (obj.getRow() == item.getRow() && obj.getCol() == item.getCol()) {
						k = true;	 
					}
				}
				if (k == false) {
					obj.setRow(-2);
					obj.setCol(-2);
					obj.setVal(".".charAt(0));
				}
			}
		}
	}
}

