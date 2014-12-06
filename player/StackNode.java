/*
 * contains cell, direction from its parent to it, its depth;
 */
 
package player;

class StackNode 
{
	Cell cell;
	int dir;
	int dep;
	StackNode(Cell c, int dir, int depth)
	{
		this.cell = c;
		this.dir = dir;
		this.dep = depth;
	}
}