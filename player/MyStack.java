/*
 * LIFO stack
 * capacity is 20
 */
 
package player;

public class MyStack<T> 
{
	private int size = 0;
	private static final int CAPACITY = 20;
	private Object elements[];

	public MyStack() 
	{
		elements = new Object[CAPACITY];
	}

	public void push(T t) 
	{
		elements[size] = t;
		size++;
	}

	public boolean isEmpty()
	{
		return (size == 0);
	}

	public T pop()
	{
		size--;
		T t = (T) elements[size];
		elements[size] = null;
		return t;
	}
}
