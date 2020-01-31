package Game.GameObjects;

import Game.GameObject;

public class Player extends GameObject
{
	public Player()
	{
		id = '@';
		character = true;
		player = true;
	}
	
	public Player(int x, int y)
	{
		id = '@';
		character = true;
		player = true;
		this.x = x;
		this.y = y;
	}
}
