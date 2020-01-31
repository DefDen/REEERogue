package Game.GameObjects;

import java.util.ArrayList;

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
	
	private ArrayList<GameObject> getNeighbors(GameObject[][] floor)
	{
		ArrayList<GameObject> r = new ArrayList<GameObject>();
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				int z;
				String check = j + " " + i;
				switch(check)
				{
				case "-1 -1":
					z = 6;
					break;

				case "0 -1":
					z = 3;
					break;

				case "1 -1":
					z = 0;
					break;

				case "-1 0":
					z = 7;
					break;

				case "0 0":
					z = 4;
					break;

				case "1 0":
					z = 1;
					break;

				case "-1 1":
					z = 8;
					break;

				case "0 1":
					z = 5;
					break;

				case "1 1":
					z = 2;
					break;

				default:
					z = -1;
				}
				try
				{							
					r.add(floor[y + j][x + i]);
				}
				catch(Exception e)
				{

				}
			}
		}
		return r;
	}
	
	public String move(GameObject[][] floor)
	{
		for(GameObject g : getNeighbors(floor))
			if(g.isPlayer())
			{
				System.out.println("The " + name + " hits you!");
				return "The " + name + " hits you!";
			}
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
				return "";
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
