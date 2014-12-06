/*
 * arraylist enable you to add element
 * capacity is 20
 */
package player;

public class MyArrayList<T> 
{
	private int pos = 0;
	private Object[] array;
	private static final int CAPACITY = 20;
	
	public MyArrayList()
	{
		array =  new Object[CAPACITY];
	}
	
	public void add(T t)
	{
		array[pos] =  t;
		pos ++;
	}
	
	public T get(int i)
	{
		return (T) array[i];
	}
	public int size()
	{
		return pos;
	}
}
