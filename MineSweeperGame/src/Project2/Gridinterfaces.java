package Project2;

public interface Gridinterfaces {
	 		
	int getNumRows() ;
	int getNumColumns(); 
	int getNumBombs(); 
	boolean [ ][ ] getBombGrid();  
	int [ ][ ] getCountGrid(); 
	boolean isBombAtLocation(int row, int column);
	int getCountAtLocation(int row, int column);
	

}
