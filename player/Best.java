package player;
/*
 Best class holds data of answer when doing minimax with alpha beta pruning
*/

public class Best {
	
	double score;
	int depth;
	Move move;
	public Best(double s, int d, Move m)
	{
		score = s;
		depth = d;
		move = m;
	}
	
	public Best()
	{
		score = 0;
		depth = 0;
		move = new Move();
	}

}
