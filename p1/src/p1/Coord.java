package p1;

public class Coord {
	private int row;
	private int col;
	private char val;
	
	public Coord() {
		row = 0;
		col = 0;
		val = 0;
	}
	public Coord(int row, int col, char val) {
		this.row = row;
		this.col = col;
		this.val = val;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public char getVal() {
		return val;
	}
	public void setVal(char val) {
		this.val = val;
	}
	
}
