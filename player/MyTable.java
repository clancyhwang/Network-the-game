/*
 * table to store <K, V> value
 * capacity is 20
 */
package player;

public class MyTable<K, V>
{
	private int size = 0;
	private int CAPACITY = 20;
	private MyEntry<K, V>[] values = new MyEntry[CAPACITY];


	public V get(K key) 
	{
		for (int i = 0; i < size; i++) 
		{
			if (values[i] != null) 
			{
				if (values[i].getKey().equals(key))
				{
					return values[i].getValue();
				}
			}
		}
		return null;
	}
	public void put(K key, V value) 
	{
		boolean insertable = true;
		for (int i = 0; i < size; i++) 
		{
			if (values[i] != null && values[i].getKey().equals(key)) 
			{
				values[i].setValue(value);
				insertable = false;
			}
		}
		if (insertable)
		{
			values[size] = new MyEntry<K, V>(key, value);
			size ++;
		}
	}
	public int size() 
	{
		return size;
	}

	public void remove(K key) 
	{
		for (int i = 0; i < size; i++) 
		{
			if (values[i].getKey().equals(key)) 
			{
			values[i] = null;
			size--;
			condenseArray(i);
			}
		}
	}	
	private void condenseArray(int start) 
	{
		for (int i = start; i < size; i++) 
		{
		values[i] = values[i + 1];
		}
	}
	public MyArrayList<K> keySet()
	{
		MyArrayList<K> keys = new MyArrayList<K>();
		for (int i = 0; i < size; i++) 
		{
			keys.add(values[i].getKey());
		}
		return keys;
	}
} 