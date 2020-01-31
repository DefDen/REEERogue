package Game.GameObjects;

import java.util.ArrayList;

import Game.GameObject;


public class Enemy extends GameObject
{
	protected int health = 1;
	protected static int sharedIdInt = 0;
	protected int idInt = sharedIdInt;

	public Enemy()
	{
		id = 'E';
		character = true;
		name = "enemy";
		sharedIdInt++;
	}

	public Enemy(int y, int x)
	{
		id = 'E';
		character = true;
		name = "enemy";
		this.x = x;
		this.y = y;
		sharedIdInt++;
	}

	public Enemy(int y, int x, int health)
	{
		id = 'E';
		character = true;
		name = "enemy";
		this.x = x;
		this.y = y;
		this.health = health;
		sharedIdInt++;
	}
	
	public boolean equals(Enemy e)
	{
		return idInt == e.idInt;
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
	
	public Enemy copy()
	{
		return new Enemy(y, x, health);
	}

	public String move(GameObject[][] floor)
	{
		return "";
	}
}
