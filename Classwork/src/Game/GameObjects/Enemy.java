package Game.GameObjects;

import java.util.ArrayList;

import Game.GameObject;


public class Enemy extends GameObject
{
	protected int health = 2;
	protected static int sharedIdInt = 0;
	protected int idInt = sharedIdInt;
	protected boolean dead = false;

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
	
	public Enemy(int y, int x, int health, int idInt)
	{
		id = 'E';
		character = true;
		name = "enemy";
		this.x = x;
		this.y = y;
		this.health = health;
		this.idInt = idInt;
		sharedIdInt++;
	}
	
	public boolean equals(Enemy e)
	{
		return idInt == e.idInt;
	}
	
	public boolean hit(int damage)
	{
		health -= damage;
		if(health < 1)
		{
			dead = true;
			return dead;
		}
		return dead;
	}
	
	public Enemy copy()
	{
		return new Enemy(y, x, health);
	}

	public String move(GameObject[][] floor)
	{
		return "";
	}
	
	public int getInt()
	{
		return idInt;
	}
	
	public boolean isDead()
	{
		return dead;
	}
}
