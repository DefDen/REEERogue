package Game.GameObjects;

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
	
	public boolean hit(int damage)
	{
		health--;
		if(health < 1)
		{
			return true;
		}
		return false;
	}
}
