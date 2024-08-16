package Project2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GridCopy implements Gridinterfaces{
	Random RNG= new Random();
	
	//Fields
	private boolean [][]bombGrid;
	private int [][]countGrid;
	private int numRows;
	private int numColumns;
	private int numBombs;
	
	//Constructors
	
	public GridCopy(){
		this.numRows = 10;
		this.numColumns = 10;
		this.numBombs = 25;
		bombGrid = new boolean [10][10];
		countGrid = new int[10][10];
		createBombGrid();
		createCountGrid();
	}
	
	
	public GridCopy(int rows, int columns){
		this.numRows = rows;
		this.numColumns = columns;
		numBombs = 25;
		bombGrid = new boolean [rows][columns];
		countGrid = new int[rows][columns];
		createBombGrid();
		createCountGrid();
	}
	
	public GridCopy(int rows, int columns, int numBombs){
		this.numRows = rows;
		this.numColumns = columns;
		this.numBombs = numBombs;
		bombGrid = new boolean [rows][columns];
		countGrid = new int[rows][columns];
		createBombGrid();
		createCountGrid();
	}
	
	
	
	
	public int getNumRows() {
		return this.numRows;
	}

	
	public int getNumColumns() {
		return this.numColumns;
	}

	
	public int getNumBombs() {
		return this.numBombs;
	}

	
	public boolean[][] getBombGrid() {
	    boolean[][] copyBombGrid = new boolean[numRows][];
	    for (int i = 0; i < numRows; i++) {
	        copyBombGrid[i] = Arrays.copyOf(bombGrid[i], numColumns);
	    }
	    return copyBombGrid;
	}

	
	public int[][] getCountGrid() {
	    int[][] copy = new int[numRows][];
	    for (int i = 0; i < numRows; i++) {
	        copy[i] = Arrays.copyOf(countGrid[i], numColumns);
	    }
	    return copy;
	}
	
	public boolean isBombAtLocation(int row, int column) {
		if(bombGrid[row][column] == true) {
			return true;
		}
		else {
			return false;
		}
	}

	
	public int getCountAtLocation(int row, int column) {
		return  countGrid[row][column];
	}

	
	public void createBombGrid() {
	    // Populating the array
	    for (int row = 0; row < bombGrid.length; row++) {
	        for (int col = 0; col < bombGrid[row].length; col++) {
	            bombGrid[row][col] = false;
	        }
	    }
	    // Populating the bombs
	    ArrayList<String> bombPositions = new ArrayList<>();
	    for (int i = 0; i < numBombs; i++) {
	        String position;
	        do {
	            int randomRow = RNG.nextInt(numRows);
	            int randomCol = RNG.nextInt(numColumns);
	            position = randomRow + "," + randomCol;
	        } while (bombPositions.contains(position));
	        bombPositions.add(position);
	        bombGrid[Integer.parseInt(position.split(",")[0])][Integer.parseInt(position.split(",")[1])] = true;
	    }
	}
	
	private void createCountGrid() {	
		for (int row = 0; row < bombGrid.length; row++) {
	        for(int col = 0; col < bombGrid[row].length; col++) {
	        	if(bombGrid[row][col]) {
	        		incrementsBombs(countGrid, row, col);
	        	}
	        }
	       }
	}
	
	
	private void incrementsBombs(int[][] bombGrid, int bombRow, int bombCol) {
	    for (int row = bombRow - 1; row <= bombRow + 1; row++) {
	        for (int col = bombCol - 1; col <= bombCol + 1; col++) {
	            if (row >= 0 && row < numRows && col >= 0 && col < numColumns) {
	                countGrid[row][col]++;
	            }
	        }
	    }
	}
	
	
	
	
	
	
	
	
	
	
	//Just to test
	public void printingBombGrid() {
		//Printing the Grid
	    for (int row = 0; row < bombGrid.length; row++) {
	        for(int col = 0; col < bombGrid[row].length; col++) {
	            if (bombGrid[row][col] == false) {
	                System.out.print("F ");
	            } else {
	                System.out.print("T ");
	            }
	        }
	        System.out.println(); 
	    }
	}
	
	public void printCountGrid(int[][] countGrid) {
	    for (int row = 0; row < countGrid.length; row++) {
	        for (int col = 0; col < countGrid[row].length; col++) {
	            System.out.print(countGrid[row][col] + " ");
	        }
	        System.out.println();
	    }
	}
}