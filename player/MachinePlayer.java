/* MachinePlayer.java */

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
package player;
import java.lang.Math;

public class MachinePlayer extends Player {
	private int machine;
	private int opponent;
	private int depth;
	private Board board;
	private final static int DIMENSION = 8;
	private final static int WINSCORE = 100;
	private int chips;
  
    private static final int DEFAULT_DEPTH = 3;


  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
	public MachinePlayer(int color) {
		machine=color;
		opponent = 1 - color;
		board= new Board(color);
		depth = DEFAULT_DEPTH;


	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this(color);
		depth = searchDepth;
	}

	// Returns a new move by "this" player.  Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove() {	
		Best best = bestMove(machine, -999, 999, depth);
		Move m = best.move;
		forceMove(m);
		return m;
	} 
	
	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) 
	{
		if (board.isValidMove(m, opponent))
		{
			validMoveByColor(m, opponent);
			return true;
		}
		else
		{
			return false;
		}
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.	
  	public boolean forceMove(Move m) 
	{
		if (board.isValidMove(m, machine))
		{
			validMoveByColor(m, machine);
			return true;
		}
		else
		{
			return false;
		}
	}
  	
  	/*
  	 * do the movement by one color, self-use method
  	 */
  	private void validMoveByColor(Move m, int color)
  	{
		if(m.moveKind == Move.ADD)
		{
			board.addChip(m, color);
		}
		else if(m.moveKind == Move.STEP)
		{
			board.stepChip(m, color);
		}
  	}
  	/*
  	 * cancel the move by one side
  	 */
  	public void undoMove(Move m, int color)
  	{
  		if(m.moveKind == Move.STEP)
  		{
  			Move retMove = new Move(m.x2, m.y2, m.x1, m.y1);
  			validMoveByColor(retMove, color);
  		}
  		else if(m.moveKind == Move.ADD)
  		{
  			board.removeChip(m.x1, m.y1, color);
  		}
  	}

	/*bestMove takes in color, initial alpha beta value, and depth of search
	* returns the best move for "color", using alpha beta pruning
	*/
	private Best bestMove(int color, double alpha, double beta, int depth) {
		Best best = new Best();
		Best answer;
		Move[] legalMoves;
		Move m;
		double evaluatedScore;
		
		if (depth == 0) {
			best.depth = 0;
			evaluatedScore = board.evaluation(machine);
			best.score = evaluatedScore;
			Move[] moves = board.allValidMoves(machine);
			int randMove = (int)(Math.random() * moves.length);
			return best;
		}

		evaluatedScore = board.evaluation(machine);
		if (evaluatedScore == WINSCORE || evaluatedScore == -WINSCORE) {
			best.depth = depth;
			best.score = evaluatedScore;
			return best;
		}

		if (color == machine) {
			best.score = alpha;
		} else if(color == opponent){
			best.score = beta;
		}
      
		legalMoves = board.allValidMoves(color);
		for(int i = 0; i< legalMoves.length; i++ ){
			m = legalMoves[i];
				if(color == machine) {
					forceMove(m);
				} else {
					opponentMove(m);
				}
			answer = bestMove(1-color,alpha,beta, depth-1);
			undoMove(m, color);
            if ((color == machine) && (answer.score > best.score ||(answer.score == best.score && answer.depth > best.depth))){
                best.move = m;
                best.score = answer.score;
                best.depth = answer.depth;
                alpha = answer.score;      
            } else if((color == opponent) &&( answer.score < best.score || ( answer.score == best.score&& answer.depth < best.depth))){
                best.move = m;
                best.score = answer.score;
                best.depth = answer.depth;
                beta = answer.score;
            }
            if (alpha >= beta) {
                return best;
            }
        }
		return best;
	}
}