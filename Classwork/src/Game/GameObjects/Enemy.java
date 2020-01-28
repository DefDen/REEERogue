package Game.GameObjects;

import java.util.ArrayList;

import Game.GameObject;

public class Enemy extends GameObject
{
	private int health = 3;

	public Enemy()
	{
		id = 'E';
		character = true;
		name = "enemy";
	}

	public Enemy(int y, int x)
	{
		id = 'E';
		character = true;
		name = "enemy";
		this.x = x;
		this.y = y;
	}

	public Enemy(int y, int x, int health)
	{
		id = 'E';
		character = true;
		name = "enemy";
		this.x = x;
		this.y = y;
		this.health = health;
	}
	
	public boolean hit(int damage)
	{
		health--;
		if(health < 1)
		{
			return true;
		}
		return false;
	}

	public void move(GameObject[][] floor)
	{
		while(true)
		{
			int newY = y + (int)(3 * Math.random() - 1);
			int newX = x + (int)(3 * Math.random() - 1);
			if(newY > 21 || newX > 79 || newY < 2 || newX < 1)
			{
				continue;
			}
			if(floor[newY][newX] != null && floor[newY][newX].isEmptySpace())
			{
				floor[y][x] = new EmptySpace();
				y = newY;
				x = newX;
				floor[y][x] = new Enemy(y, x, health);
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
