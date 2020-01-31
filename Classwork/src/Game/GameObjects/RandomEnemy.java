package Game.GameObjects;

import Game.GameObject;

public class RandomEnemy extends Enemy
{
	
	
	public RandomEnemy()
	{
		super();
		id = 'R';
		name = "rat";
		health = 2;
	}

	public RandomEnemy(int y, int x)
	{
		super(y, x);
		id = 'R';
		name = "rat";
		health = 2;
	}

	public RandomEnemy(int y, int x, int health)
	{
		super(y, x, health);
		id = 'R';
		name = "rat";
		health = 2;
	}
	
	public RandomEnemy copy()
	{
		return new RandomEnemy(y, x, health);
	}
	
	public void move(GameObject[][] floor)
	{
		while(true)
		{
			int newY = y + (int)(4 * Math.random() - 2);
			int newX = x + (int)(4 * Math.random() - 2);
			if(newY >= floor.length || newX >= floor[0].length || newY < 2 || newX < 1)
			{
				continue;
			}
			if(floor[newY][newX].isEmptySpace())
			{
				floor[y][x] = new EmptySpace();
				y = newY;
				x = newX;
				floor[y][x] = copy();
				return;
			}
		}
//		GameObject[] neighbors = new GameObject[9];
//		for(int i = -1; i < 2; i++)
//		{
//			for(int j = -1; j < 2; j++)
//			{
//				try
//				{							
//					neighbors.add(floor[y + j][x + i]);
//				}
//				catch(Exception e)
//				{
//
//				}
//			}
//		}
//		ArrayList<Integer> ints = new ArrayList<Integer>();
//		for(int k = 0; k < 9; k++)
//		{
//			ints.add(k);
//		}
//		while(true)
//		{
//			int check = (int)(ints.size() * Math.random());
//			if(neighbors.get(ints.remove(check)).isTerrain())
//			{
//				floor[y][x] = new EmptySpace();
//				Enemy add = new Enemy();
//				neighbors.get(check) = add;
//			}
//		}
	}
}
