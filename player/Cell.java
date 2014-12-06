package player;

/*
 * a class to store each cell in the game board
 */
public class Cell{
	private static final int BLACK=0;
	private static final int WHITE=1;
	private static final int NONE=2;

	private int x;  // index x
	private int y;  // index y
	private int color; //which color a cell is holding
	private Cell[] connnectedCells; // create an anrry that store all the neighbor of a cell
     
  
// a constructor to create a cell , with positon (x,y), without being occupied
	public Cell(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.color=NONE;
		this.connnectedCells = new Cell[8];
	}	

//a constructor to create a cell with a sepecific occupancy
	public Cell(int x,int y, int color)
	{
		this.x=x;
		this.y=y;
		this.color=color;
		this.connnectedCells = new Cell[8];
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}


	public int getColor()
	{
		return color ;  	
	}

	public int setColor(int color)
	{
		return this.color = color;
	}
}
