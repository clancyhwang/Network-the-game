/*Board.java*/

package player;

public class Board
{

	private static final int 
							UP = 0, 
							UPRIGHT = 1,
							RIGHT = 2,
							DOWNRIGHT = 3,  
							DOWN = 4,
							DOWNLEFT = 5,
							LEFT = 6,
							UPLEFT = 7,
							NODIRECTION = 8;
	
	private static final int FIRSTROW=0;
	private static final int FIRSTCOLUMN=0;
	private static final int LASTROW=7;
	private static final int LASTCOLUMN=7;
	private static final int TOTALNUMOFCHIPS=10;
	private static final int BLACK=0;
	private static final int WHITE=1;
	private static final int NONE=2;

	private static final int DIMENSION = 8;
	
	private static final int WINSCORE = 100;
	private static final int TOTALCONNECT = 80;
	
	private Cell[][] board;
	private int color;
	private int numWhiteChips ;// keep the track of the number of white chips on the board, can't be bigger than 10
	private int numBlackChips ;// keef the track of  the number of black chips on the board, can't be bigger than 10
   
   
   
   
   
   
	/** constructor for a empty board belongs to player p;
	  * Cell.color ==2, if a Cell is not used
	**/
	public Board(int c){
		board=new Cell[DIMENSION][DIMENSION];
		this.color = c;
		numWhiteChips=0;
		numBlackChips=0;
		int x;
		int y;
		for (x=0;x<DIMENSION;x++){
			for (y=0;y<DIMENSION;y++){
				board[x][y]=new Cell(x,y);
			}
		}
	}
	
	
	 /*
	public method to check if the play is valid or not
	*/
	public boolean isValidMove(Move move, int color){
		//check move type first
		int m = move.moveKind;
		// quit move is alway valid
		if (m == Move.QUIT ){
			return true;
		}
		// check if a add move is valid
		else if (m == Move.ADD){
		// reject add a black/white chip if the total number of black/white chips is 10
			if ((color == BLACK && numBlackChips == TOTALNUMOFCHIPS)||(color == WHITE && numWhiteChips== TOTALNUMOFCHIPS)) {
				return false;
			}
			else{
				return isValidDestination(move, color);
			}
		}
		else{
			if ((color==BLACK && numBlackChips != TOTALNUMOFCHIPS) || (color == WHITE && numWhiteChips != TOTALNUMOFCHIPS)){
				return false;
			}else{
				return isValidDestination(move, color);
			}
		}
	}
  
  
	/*
	return an array of all valid moves  
	*/
	public Move[] allValidMoves(int color){
		int x;
		int y;
		int[][] oldCoOrdinate=new int[TOTALNUMOFCHIPS][2]; //to store all the old co-ordinates of the argument "color" chips on the board
		int totalnum=0; //count variable also total number of given "color " of chips on the board
		int i;
		int j;
		Move[] allMove=new Move[40*TOTALNUMOFCHIPS]; // a Move array that stores all the valid moves

		// return a int array that stores all the black/white chips' co-ordinate on the board
		for (i=0; i<DIMENSION;i++){
			for (j=0;j<DIMENSION;j++){
				if (board[i][j].getColor()==color){
					oldCoOrdinate[totalnum][0]=i;
						oldCoOrdinate[totalnum][1]=j;
						totalnum++;
				}
			}
		}
		
		//if "COLOR" CHIP == TOTALNUMCHIPS , Move is a step move
		if( (color == BLACK  && numBlackChips == TOTALNUMOFCHIPS) || (color == WHITE && numWhiteChips == TOTALNUMOFCHIPS) ){
			int k=0; //count variable, also the total number of valid step moves
			for(int n=0; n< TOTALNUMOFCHIPS ; n++){
				x= oldCoOrdinate[n][0];
				y= oldCoOrdinate[n][1];
				for (i=0; i<DIMENSION;i++){
					for (j=0;j<DIMENSION;j++){
						Move move=new Move(i,j,x,y); // creat a step move
						if ( this.isValidMove(move, color) ){
							allMove[k]=move;
							k++;
						}
					}
				}
			}
			int retSize = 0;
			for(int n = 0; n < allMove.length; n ++)
			{
				if(allMove[n] == null)
				{
					break;
				}
				retSize ++;
			}
			Move[] retMoves = new Move[retSize];
			for(int n = 0; n < retSize; n ++)
			{
				retMoves[n] = allMove[n];
			}
			return retMoves;
		}else{
			int k=0;
			for (i=0; i<DIMENSION;i++){
				for (j=0;j<DIMENSION;j++){
					Move move=new Move(i,j);
					if ( this.isValidMove(move, color)){
						allMove[k]=move;
						k++;
					}
				}
			}
			int retSize = 0;
			for(int n = 0; n < allMove.length; n ++)
			{
				if(allMove[n] == null)
				{
					break;
				}
				retSize ++;
			}
			Move[] retMoves = new Move[retSize];
			for(int n = 0; n < retSize; n ++)
			{
				retMoves[n] = allMove[n];
			}
			return retMoves;
		}
	}

	/*
	add a chip to (x,y) position on the borad
	*/
    public void addChip(Move move, int color){
		board[move.x1][move.y1].setColor(color);
		if (color==BLACK){
			numBlackChips++;
		}else if(color == WHITE){
			numWhiteChips++;
		}
    }
    

    /*
	move a black(0)/ white(1) chip form one position (x1, y1) to another position (x2, y2)
	*/
	public void stepChip(Move move, int color){
		board[move.x1][move.y1].setColor(color);
		board[move.x2][move.y2].setColor(NONE);
    }
	



	/* 
    remove the takein parameter "color" chip in coordinate(x,y) on the board
	*/
	public void removeChip(int x, int y, int color)
	{
		board[x][y].setColor(NONE);
		if(color == WHITE)
		{
			this.numWhiteChips--;
		}
		else if(color == BLACK)
		{
			this.numBlackChips--;
		}
	}	
		
	
	
	
	/*
	 *return a Cell array represents all connected cells to a chip
	 */
	public Cell[] connectedCells(Cell chip)
  	{
  		int x = chip.getX();
  		int y = chip.getY();
  		int color = chip.getColor();
  		int topBound = (color == BLACK? FIRSTROW: FIRSTROW + 1);
  		int botBound = (color == BLACK? LASTROW: LASTROW - 1);
  		int leftBound = (color == BLACK? FIRSTCOLUMN + 1: FIRSTCOLUMN);
  		int rightBound = (color == BLACK? LASTCOLUMN - 1: LASTCOLUMN);
  		Cell[] connectedCells = new Cell[8];		
  		//initialize all connectedCells to null
  		for(int i = 0; i < 8; i ++)
  		{
  			connectedCells[i] = null;
  		}
  		//check if there is a connected node in UP direction
  		for(int i = y - 1; i >= topBound; i --)
  		{
  			if(board[x][i].getColor() != color && board[x][i].getColor() != NONE)
  			{
  				connectedCells[UP] = null;
  				break;
  			}
  			if(board[x][i].getColor() == color)
  			{
  				connectedCells[UP] = board[x][i];
  				break;
  			}
  		}
  		//check if there is a connected node in UPRIGHT direction
  		for(int i = y - 1, j = x + 1; i >= topBound && j <= rightBound; i --, j ++)
  		{
	  		if(board[j][i].getColor() != NONE && board[j][i].getColor() != color)
	  		{
	  			connectedCells[UPRIGHT] = null;
	  			break;
	  		}
	  		if(board[j][i].getColor() == color)
	  		{
	  			connectedCells[UPRIGHT] = board[j][i];
	  			break;
	  		}
  		}	
  		//check if there is a connected node in RIGHT direction
  		for(int i = x + 1; i <= rightBound; i ++)
  		{
  			if(board[i][y].getColor() != color && board[i][y].getColor() != NONE)
  			{
  				connectedCells[RIGHT] = null;
  				break;
  			}
  			if(board[i][y].getColor() == color)
  			{
  				connectedCells[RIGHT] = board[i][y];
  				break;
  			}
  		}
  		//check if there is a connected node in DOWNRIGHT direction
  		for(int i = y + 1, j = x + 1; i <= botBound && j <= rightBound; i ++, j ++)
  		{
	  		if(board[j][i].getColor() != NONE && board[j][i].getColor() != color)
	  		{
	  			connectedCells[DOWNRIGHT] = null;
	  			break;
	  		}
	  		if(board[j][i].getColor() == color)
	  		{
	  			connectedCells[DOWNRIGHT] = board[j][i];
	  			break;
	  		}
  		}
  		//check if there is a connected node in DOWN direction
  		for(int i = y + 1; i <= botBound; i ++)
  		{
  			if(board[x][i].getColor() != color && board[x][i].getColor() != NONE)
  			{
  				connectedCells[DOWN] = null;
  				break;
  			}
  			if(board[x][i].getColor() == color)
  			{
  				connectedCells[DOWN] = board[x][i];
  				break;
  			}
  		}
  		//check if there is a connected node in DOWNLEFT direction
  		for(int i = y + 1, j = x - 1; i <= botBound && j >= leftBound; i ++, j --)
  		{
	  		if(board[j][i].getColor() != NONE && board[j][i].getColor() != color)
	  		{
	  			connectedCells[DOWNLEFT] = null;
	  			break;
	  		}
	  		if(board[j][i].getColor() == color)
	  		{
	  			connectedCells[DOWNLEFT] = board[j][i];
	  			break;
	  		}
  		}
 	
  		//check if there is a connected node in LEFT direction
  		for(int i = x - 1; i >= leftBound; i --)
  		{
  			if(board[i][y].getColor() != color && board[i][y].getColor() != NONE)
  			{
  				connectedCells[LEFT] = null;
  				break;
  			}
  			if(board[i][y].getColor() == color)
  			{
  				connectedCells[LEFT] = board[i][y];
  				break;
  			}
  		}
  		//check if there is a connected node in TOPLEFT direction
  		for(int i = y - 1, j = x - 1; i >= botBound && j >= rightBound; i --, j --)
  		{
	  		if(board[j][i].getColor() != NONE && board[j][i].getColor() != color)
	  		{
	  			connectedCells[UPLEFT] = null;
	  			break;
	  		}
	  		if(board[j][i].getColor() == color)
	  		{
	  			connectedCells[UPLEFT] = board[j][i];
	  			break;
	  		}
  		}
  		return connectedCells;
  	}
	
	/*
	 *return true if a board has network for given color, false otherwise
	 */
	public boolean hasNetwork(int color)
	{
		StackNode start;

		if(color == WHITE)
		{
			for(int i = FIRSTROW; i < DIMENSION; i++)
			{
				if(board[FIRSTCOLUMN][i].getColor() == WHITE)//choose a start cell in left goal, white color
				{
					start = new StackNode(board[FIRSTCOLUMN][i], NODIRECTION, 1);
					MyStack<StackNode> stack = new MyStack<StackNode>();
					MyTable<Cell, Integer> visited = new MyTable<Cell, Integer>();//to store the visited Cell and its depth
					stack.push(start);
					
					while(!stack.isEmpty())//dfs to find the network with length >= 6
					{
						StackNode curr = stack.pop();

						Cell[] conn = connectedCells(curr.cell);
						visited.put(curr.cell, curr.dep);
						
						int temp_conns = 0;
						for(int j = 0; j < 8; j ++)//8 possible directions in total, for each j represents a direction curr->a branch
						{	
							StackNode branch = new StackNode(conn[j], j, curr.dep + 1);
							//in a network, can only be two cells seperately in two goals, and the direction curr.parent->curr can't be equal to curr->a branch
							if(branch.cell == null || ifInLeftGoal(branch.cell) || j == curr.dir || (ifInRightGoal(branch.cell) && branch.dep < 6))
							{
								continue;
							}
							if(ifInRightGoal(branch.cell) && branch.dep >= 6)
							{
								return true;
							}
							if(visited.get(branch.cell) == null )
							{
								stack.push(branch);
								temp_conns ++;
							}
						}
						if(temp_conns == 0)//means curr.cell can't be expanded more, set all nodes with higher depth than it be unvisited, try to expand it again
						{
							int x = 0;
							MyArrayList<Cell> toDelete = new MyArrayList<Cell>();
							MyArrayList<Cell> keys = visited.keySet();
							for(int a = 0; a < keys.size(); a ++)
							{
								if(visited.get(keys.get(a)) >= curr.dep && keys.get(a) != curr.cell)
								{
									toDelete.add(keys.get(a));
									x ++;
								}
							}
							for(int e = 0; e < toDelete.size(); e ++)
							{
								visited.remove(toDelete.get(e));
							}
							if(x != 0)
							{
								stack.push(curr);	
							}
						}
						temp_conns = 0;
					}
					
				}
			}
			return false;
		}
		
		else
		{
			for(int i = FIRSTCOLUMN; i < DIMENSION; i++)
			{
				if(board[i][FIRSTROW].getColor() == BLACK)//choose a start cell in top goal, black color
				{
					start = new StackNode(board[i][FIRSTROW], NODIRECTION, 1);
					MyStack<StackNode> stack = new MyStack<StackNode>();
					MyTable<Cell, Integer> visited = new MyTable<Cell, Integer>();  //to store the Cell and its depth
					stack.push(start);
					while(!stack.isEmpty())//dfs to find the network with length >= 6
					{
						StackNode curr = stack.pop();
						Cell[] conn = connectedCells(curr.cell);
						visited.put(curr.cell, curr.dep);
						
						int temp_conns = 0;
						for(int j = 0; j < 8; j ++)//8 possible directions in total, for each j represents a direction curr->a branch
						{	
							StackNode branch = new StackNode(conn[j], j, curr.dep + 1);
							//in a network, can only be two cells seperately in two goals, and the direction curr.parent->curr can't be equal to curr->a branch
							if(branch.cell == null || ifInTopGoal(branch.cell) || j == curr.dir || (ifInBotGoal(branch.cell) && branch.dep < 6))
							{
								continue;
							}
							if(ifInBotGoal(branch.cell) && branch.dep >= 6)
							{
								return true;
							}
							if(visited.get(branch.cell) == null )
							{
								stack.push(branch);
								temp_conns ++;
							}
						}
						if(temp_conns == 0)//means curr.cell can't be expanded more, set all nodes with higher depth than it be unvisited, try to expand it again
						{
							int x = 0;
							MyArrayList<Cell> toDelete = new MyArrayList<Cell>();
							MyArrayList<Cell> keys = visited.keySet();
							for(int a = 0; a < keys.size(); a ++)
							{
								if(visited.get(keys.get(a)) >= curr.dep && keys.get(a) != curr.cell)
								{
									toDelete.add(keys.get(a));
									x ++;
								}
							}
							for(int e = 0; e < toDelete.size(); e ++)
							{
								visited.remove(toDelete.get(e));
							}
							if(x != 0)
							{
								stack.push(curr);	
							}
						}
						temp_conns = 0;
					}
					
				}
			}
			return false;
		}
	}
	

	/*	evaluation takes in colorcalled by module 6) bestMove
	returns the score to assign to the color in current board
    */
    public double evaluation(int color) {
		Cell[] connection;
	    int numBlackConnec=0;
	    int numWhiteConnec=0;
		if(hasNetwork(color))
		{  
	    	return WINSCORE;        
	    }else if(hasNetwork(1-color))
		{         
	    	return -WINSCORE;        
	    }
	    for (int x=0;x<DIMENSION;x++){
	    	for (int y=0;y<DIMENSION;y++){
	    		if(board[x][y].getColor()==BLACK){               
	    			connection=connectedCells(board[x][y]); 
					for(int i = 0; i < connection.length; i ++)
					{
						if(connection[i] != null)
						{
							numBlackConnec ++;
						}
					}         
	    		}	             
	    		else if(board[x][y].getColor()==WHITE){              
	    			connection=connectedCells(board[x][y]);
	              	for(int i = 0; i < connection.length; i ++)
					{
						if(connection[i] != null)
						{
							numWhiteConnec ++;
						}
					}               
	    		}	           
	    	}	         
	    }	          
	    if (color == BLACK) {
	    	return (double)(numBlackConnec-numWhiteConnec)/TOTALCONNECT*WINSCORE;	        
	    } 	         
	    else { 
	    	return (double)(numWhiteConnec-numBlackConnec)/TOTALCONNECT*WINSCORE;	          
	    }   
		
	}
	

	
	
/////////////////////////////////////////////private methods for internal use only//////////////////////////////////////////////
    /*
	return true if the place the palyer wants to put a chip on is a valid place, return false if is not a valid place
    */

    private boolean isValidDestination(Move move, int color){
      // No chip may be placed in any of the four corners
		if( (move.x1== FIRSTCOLUMN && move.y1== FIRSTROW) || 
                 (move.x1==LASTCOLUMN&& move.y1==FIRSTROW) || 
                 (move.x1==FIRSTCOLUMN && move.y1==LASTROW) || 
                 (move.x1==LASTCOLUMN && move.y1==LASTROW) ){
        return false;
		}  
      // black chips can't be put on white player's goal area or conner squares  
		else if( (color == BLACK) && ((move.x1== FIRSTCOLUMN)|| (move.x1== LASTCOLUMN)) ){
			return false;
		} 
      // white chips can't be put on black player's goal area or conner squar
		else if( (color == WHITE) && ((move.y1== FIRSTROW )||(move.y1==  LASTROW)) ) {
			return false;
		} 
      // No chip may be placed in a square that is already occupied
		else if (board[move.x1][move.y1].getColor() != NONE) {
			return false;
		}
       // no cluster will exsit 
		else{
			int i;
			int j;
			int num=0;
			int neighborX=0; //the neighbor who has the same "color", it's position on the bigBoard
			int neighborY=0; // the neighbor who has the same "color", it's position on the bigBoard
			for (i=move.x1-1; i< move.x1+2; i++){
				for (j = move.y1-1; j< move.y1 +2 ; j++){
					if(i>=FIRSTCOLUMN && i <= LASTCOLUMN && j >=FIRSTROW && j <= LASTROW){
						if ( (move.moveKind == Move.ADD || !(move.x2 == i && move.y2 ==j) ) &&  board[i][j].getColor() == color  ){
							num++;
							neighborX = i;
							neighborY = j;
						}
					}
				}
			}
			if (num >= 2){
				return false;
			}else if (num == 0){
				return true;
			}else{
				for (i= neighborX-1 ; i< neighborX +2; i++){
					for (j = neighborY -1 ; j <neighborY +2 ; j++){
						if (i>=FIRSTCOLUMN && i <= LASTCOLUMN && j >=FIRSTROW && j <= LASTROW){
							if ( (!(i==neighborX && j==neighborY)) &&
							( move.moveKind == Move.ADD || !(move.x2 == i && move.y2 ==j)) &&
								board[i][j].getColor() == color  ){
							return false;
							}			
						}
					}
				}
				return true;
			}
        }
    }

    /* checks if the cell is the left goal area, returns true if yes, no o/w  */
	private boolean ifInLeftGoal(Cell c)
	{
		return (c.getX() == 0);
	}
	/* checks if the cell is the right goal area, returns true if yes, no o/w  */
	private boolean ifInRightGoal(Cell c)
	{
		return (c.getX() == 7);
	}
	/* checks if the cell is the top goal area, returns true if yes, no o/w  */
	private boolean ifInTopGoal(Cell c)
	{
		return (c.getY() == 0);
	}
	/* checks if the cell is the bottom goal area, returns true if yes, no o/w  */
	private boolean ifInBotGoal(Cell c)
	{
		return (c.getY() == 7);
	}
	
	
	
	
}
	
